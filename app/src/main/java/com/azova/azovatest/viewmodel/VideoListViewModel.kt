package com.azova.azovatest.viewmodel

import android.databinding.BaseObservable
import com.azova.azovatest.model.VideoModel

class VideoListViewModel(val videoModel: VideoModel) : BaseObservable() {

    val title: String = videoModel.title

    val imageUrl: String = videoModel.thumb

}