package com.azova.azovatest.viewmodel

import android.databinding.BaseObservable
import android.databinding.Bindable
import android.view.View
import com.azova.azovatest.core.IFileDownloadListener
import com.azova.azovatest.model.VideoModel

class VideoListViewModel(private val videoModel: VideoModel, private val filDownloadListener: IFileDownloadListener) : BaseObservable() {

    val title: String = videoModel.title

    val imageUrl: String = videoModel.thumb

    @Bindable
    var isDownloadDisplay = (videoModel.downloadState == 1)

    @Bindable
    var isProgressDisplay = (videoModel.downloadState == 2)


    @Bindable
    var isVideoViewVisible = (videoModel.downloadState == 3)

    val downloadClick = View.OnClickListener {
        filDownloadListener.startDownload(videoModel)
        isDownloadDisplay = false
        isProgressDisplay = true
        notifyChange()
    }

}