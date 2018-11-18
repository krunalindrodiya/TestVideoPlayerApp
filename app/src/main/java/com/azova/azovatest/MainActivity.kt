package com.azova.azovatest

import android.os.Bundle
import android.os.Environment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.azova.azovatest.adapter.VideoListAdapter
import com.azova.azovatest.core.IFileDownloadListener
import com.azova.azovatest.core.IMainPresenter
import com.azova.azovatest.core.IMainView
import com.azova.azovatest.impl.MainPresenterImpl
import com.azova.azovatest.model.VideoModel
import com.azova.azovatest.model.VideoModelList
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

class MainActivity : AppCompatActivity(), IMainView, IFileDownloadListener {


    private val videoPath: File by lazy {
        getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
    }

    private var adapter: VideoListAdapter? = null


    private val presenter: IMainPresenter = MainPresenterImpl(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter.setVideoPath(videoPath)
        //get Video=list from json file.
        presenter.getVideoList(this)
    }

    override fun videoListResponse(videoList: VideoModelList) {
        adapter = VideoListAdapter(this, videoList.videoList, this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }


    override fun onDestroy() {
        presenter.unbindView()
        super.onDestroy()
    }

    override fun startDownload(videoModel: VideoModel) {
        videoModel.downloadState = 2
        adapter?.updateVideoModel(videoModel)
        presenter.fileDownload(videoModel)
    }

    override fun fileDownloadComplete(videoModel: VideoModel, isDownload: Boolean) {
        if (isDownload) {
            videoModel.downloadState = 3
        } else {
            videoModel.downloadState = 1
        }
        adapter?.updateVideoModel(videoModel)
    }
}
