package com.azova.azovatest.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.azova.azovatest.R
import com.azova.azovatest.core.IFileDownloadListener
import com.azova.azovatest.databinding.RowVideoListBinding
import com.azova.azovatest.model.VideoModel
import com.azova.azovatest.viewmodel.VideoListViewModel

class VideoListAdapter(context: Context, private val videoList: ArrayList<VideoModel>,
                       private val fileDownloadListener: IFileDownloadListener) : RecyclerView.Adapter<VideoListAdapter.ViewHolder>() {

    private val inflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<RowVideoListBinding>(inflater, R.layout.row_video_list, p0, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = videoList.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val videoListViewModel = VideoListViewModel(videoList[position], fileDownloadListener)
        viewHolder.setModel(videoListViewModel)
    }

    fun updateVideoModel(videoModel: VideoModel) {
        val position = videoList.indexOf(videoList.first { model -> model.id == videoModel.id })
        videoList[position] = videoModel
        notifyItemChanged(position)
    }

    class ViewHolder(private val binding: RowVideoListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setModel(videoListViewModel: VideoListViewModel) {
            binding.model = videoListViewModel
        }
    }

}