package com.azova.azovatest

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.azova.azovatest.adapter.VideoListAdapter
import com.azova.azovatest.core.IMainPresenter
import com.azova.azovatest.core.IMainView
import com.azova.azovatest.impl.MainPresenterImpl
import com.azova.azovatest.model.VideoModelList
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), IMainView {

    val presenter: IMainPresenter = MainPresenterImpl(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //get Video=list from json file.
        presenter.getVideoList(this)
    }

    override fun videoListResponse(videoList: VideoModelList) {
        val adapter = VideoListAdapter(this, videoList.videoList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }


    override fun onDestroy() {
        presenter.unbindView()
        super.onDestroy()
    }

}
