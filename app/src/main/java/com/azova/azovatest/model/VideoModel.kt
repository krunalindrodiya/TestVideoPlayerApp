package com.azova.azovatest.model

import com.google.gson.annotations.SerializedName

/**
 * Video model.
 */
data class VideoModel(@SerializedName("id") val id: Int,
                      @SerializedName("description") val description: String, @SerializedName("subtitle") val subtitle: String,
                      @SerializedName("thumb") val thumb: String, @SerializedName("title") val title: String,
                      @SerializedName("sources") val sources: ArrayList<String>,
                      var downloadState: Int = 1,
                      var videoPath: String = "")

//Donwloading state [1-> download pending, 2-> progress, 3-> download complete.]