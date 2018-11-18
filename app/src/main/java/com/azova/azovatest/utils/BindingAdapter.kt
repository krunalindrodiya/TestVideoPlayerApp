package com.azova.azovatest.utils

import android.databinding.BindingAdapter
import android.net.Uri
import android.widget.ImageView
import android.widget.MediaController
import android.widget.VideoView
import com.azova.azovatest.BuildConfig
import com.azova.azovatest.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


object BindingAdapter {


    @BindingAdapter("imageUrl")
    @JvmStatic
    fun ImageView.setUpImageInImageView(imageUrl: String) {
        val options = RequestOptions()
        options.centerCrop()
        options.placeholder(R.drawable.place_holder)
        Glide.with(this.context).load(BuildConfig.IMAGE_LOAD_URL + imageUrl).apply(options).into(this)
    }

    @BindingAdapter("videoUrl")
    @JvmStatic
    fun VideoView.setUpVideoUrl(videoUrl: String? = null) {
        videoUrl?.let {
            val mc = MediaController(context)
            mc.setAnchorView(this)
            mc.setMediaPlayer(this)
            val video = Uri.parse(videoUrl)
            this.setMediaController(mc)
            this.setVideoURI(video)
            this.requestFocus()
        }
    }


}