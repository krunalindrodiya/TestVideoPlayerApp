package com.azova.azovatest.core

import android.content.Context

interface IMainPresenter {

    fun getVideoList(context: Context)

    fun unbindView()
}