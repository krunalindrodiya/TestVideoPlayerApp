package com.azova.azovatest.impl

import android.content.Context
import com.azova.azovatest.core.IMainPresenter
import com.azova.azovatest.core.IMainView
import com.azova.azovatest.model.VideoModelList
import com.google.gson.Gson
import java.io.IOException

class MainPresenterImpl(var view: IMainView?) : IMainPresenter {
    override fun getVideoList(context: Context) {
        val jsonString = loadJSONFromAsset(context)
        val videoListModel = Gson().fromJson(jsonString, VideoModelList::class.java)
        videoListModel?.let {
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

}