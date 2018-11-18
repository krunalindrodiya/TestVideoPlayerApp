package com.azova.azovatest.core

import android.content.Context
import com.azova.azovatest.model.VideoModel
import java.io.File

interface IMainPresenter {

    fun setVideoPath(videoPath: File)

    fun getVideoList(context: Context)

    fun fileDownload(videoModel: VideoModel)

    fun unbindView()
}