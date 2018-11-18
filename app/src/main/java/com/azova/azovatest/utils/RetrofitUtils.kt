package com.azova.azovatest.utils

import retrofit2.Retrofit

object RetrofitUtils {

    fun getRetrofitObject(): Retrofit {
        return Retrofit.Builder().baseUrl("http://commondatastorage.googleapis.com").build()
    }

}