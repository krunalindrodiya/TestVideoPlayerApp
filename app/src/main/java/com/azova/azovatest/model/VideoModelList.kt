package com.azova.azovatest.model

import com.google.gson.annotations.SerializedName

data class VideoModelList(@SerializedName("videos") val videoList: ArrayList<VideoModel>)