package com.azova.azovatest.core

import com.azova.azovatest.model.VideoModel
import com.azova.azovatest.model.VideoModelList

interface IMainView {

    fun videoListResponse(videoList: VideoModelList)

    fun fileDownloadComplete(videoModel: VideoModel, isDownload: Boolean)

}