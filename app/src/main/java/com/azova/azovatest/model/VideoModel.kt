package com.azova.azovatest.model

import com.google.gson.annotations.SerializedName

/**
 * Video model.
 */
data class VideoModel(@SerializedName("description") val description: String, @SerializedName("subtitle") val subtitle: String,
                      @SerializedName("thumb") val thumb: String, @SerializedName("title") val title: String,
                      @SerializedName("sources") val sources: ArrayList<String>)