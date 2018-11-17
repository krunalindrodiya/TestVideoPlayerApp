package com.azova.azovatest.utils

import android.databinding.BindingAdapter
import android.widget.ImageView
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

}