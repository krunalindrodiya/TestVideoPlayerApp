package com.azova.azovatest.core

import com.azova.azovatest.model.VideoModel

interface IFileDownloadListener {

    fun startDownload(videoModel: VideoModel)

}