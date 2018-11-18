package com.azova.azovatest.impl

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import android.webkit.URLUtil
import com.azova.azovatest.core.IFileDownloadService
import com.azova.azovatest.core.IMainPresenter
import com.azova.azovatest.core.IMainView
import com.azova.azovatest.model.VideoModel
import com.azova.azovatest.model.VideoModelList
import com.azova.azovatest.utils.RetrofitUtils
import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.*


class MainPresenterImpl(var view: IMainView?) : IMainPresenter {

    private var videoPath: File? = null

    override fun setVideoPath(videoPath: File) {
        this.videoPath = videoPath
    }

    private val fileDownloadService = RetrofitUtils.getRetrofitObject().create(IFileDownloadService::class.java)

    override fun fileDownload(videoModel: VideoModel) {

        if (videoModel.sources.size > 0) {
            val call = fileDownloadService.downloadFile(videoModel.sources[0])
            val filename = URLUtil.guessFileName(videoModel.sources[0], null, null)

            call.enqueue(object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.e(this.javaClass.simpleName, "error ${t.message}");
                    view?.fileDownloadComplete(videoModel, false)
                }

                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if (response.isSuccessful) {
                        Log.d(this.javaClass.simpleName, "server contacted and has file")
                        //download video on background thread. Here we can go with service as well.
                        object : AsyncTask<Void, Void, Boolean>() {
                            override fun doInBackground(vararg voids: Void): Boolean {
                                val writtenToDisk = response.body()?.let { writeResponseBodyToDisk(it, filename) }
                                Log.d(this.javaClass.simpleName, "file download was a success? $writtenToDisk")
                                return writtenToDisk ?: false
                            }

                            override fun onPostExecute(result: Boolean?) {
                                super.onPostExecute(result)
                                result?.let {
                                    if (it) {
                                        val filePath = File(videoPath, filename)
                                        videoModel.videoPath = filePath.absolutePath
                                        view?.fileDownloadComplete(videoModel, true)
                                    } else {
                                        view?.fileDownloadComplete(videoModel, false)
                                    }
                                }

                            }
                        }.execute()
                    } else {
                        Log.d(this.javaClass.simpleName, "server contact failed")
                        view?.fileDownloadComplete(videoModel, false)
                    }
                }
            })
        }
    }

    override fun getVideoList(context: Context) {
        val jsonString = loadJSONFromAsset(context)
        val videoListModel = Gson().fromJson(jsonString, VideoModelList::class.java)
        videoListModel?.let {

            videoListModel.videoList.forEach { videoModel ->

                if (videoModel.sources.isNotEmpty()) {
                    val fileName = URLUtil.guessFileName(videoModel.sources[0], null, null)
                    val file = File(videoPath, fileName)
                    if (file != null && file.exists()) {
                        videoModel.videoPath = file.absolutePath
                        videoModel.downloadState = 3
                    } else {
                        videoModel.downloadState = 1
                    }
                }
            }

            view?.videoListResponse(it)
        }
    }

    override fun unbindView() {
        view = null
    }

    private fun loadJSONFromAsset(context: Context): String? {
        var json: String? = null
        try {
            val inputStream = context.assets.open("video_list.json")
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            json = String(buffer)
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
        return json
    }

    private fun writeResponseBodyToDisk(body: ResponseBody, filename: String): Boolean {
        try {
            // todo change the file location/name according to your needs
            val futureStudioIconFile = File(videoPath, filename)

            var inputStream: InputStream? = null
            var outputStream: OutputStream? = null

            try {
                val fileReader = ByteArray(4096)

                val fileSize = body.contentLength()
                var fileSizeDownloaded: Long = 0

                inputStream = body.byteStream()
                outputStream = FileOutputStream(futureStudioIconFile)

                while (true) {
                    val read = inputStream?.read(fileReader)

                    if (read == -1) {
                        break
                    }
                    read?.let {
                        outputStream?.write(fileReader, 0, read)
                        fileSizeDownloaded += read.toLong()
                        Log.d(this.javaClass.simpleName, "file download: $fileSizeDownloaded of $fileSize")
                    }
                }

                outputStream?.flush()

                return true
            } catch (e: IOException) {
                return false
            } finally {
                if (inputStream != null) {
                    inputStream.close()
                }

                if (outputStream != null) {
                    outputStream.close()
                }
            }
        } catch (e: IOException) {
            return false
        }

    }


}