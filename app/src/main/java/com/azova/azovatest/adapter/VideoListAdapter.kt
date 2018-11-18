package com.azova.azovatest.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.azova.azovatest.R
import com.azova.azovatest.core.IFileDownloadListener
import com.azova.azovatest.databinding.RowVideoListBinding
import com.azova.azovatest.model.VideoModel
import com.azova.azovatest.viewmodel.VideoListViewModel
import im.ene.toro.ToroPlayer
import im.ene.toro.ToroUtil
import im.ene.toro.exoplayer.ExoPlayerViewHelper
import im.ene.toro.media.PlaybackInfo
import im.ene.toro.widget.Container


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
        videoList[position].videoPath.let {
            val uri = Uri.parse(it)
            viewHolder.bind(uri)
        }
    }

    fun updateVideoModel(videoModel: VideoModel) {
        val position = videoList.indexOf(videoList.first { model -> model.id == videoModel.id })
        videoList[position] = videoModel
        notifyItemChanged(position)
    }

    class ViewHolder(private val binding: RowVideoListBinding) : RecyclerView.ViewHolder(binding.root), ToroPlayer {

        var helper: ExoPlayerViewHelper? = null
        var mediaUri: Uri? = null

        fun bind(media: Uri) {
            this.mediaUri = media
        }

        fun setModel(videoListViewModel: VideoListViewModel) {
            binding.model = videoListViewModel
        }

        override fun isPlaying(): Boolean {
            return helper?.isPlaying ?: false
        }

        override fun getPlayerView(): View {
            return binding.videoView
        }

        override fun pause() {
            helper?.pause()
        }

        override fun wantsToPlay(): Boolean {
            return ToroUtil.visibleAreaOffset(this, itemView.parent) >= 0.85;
        }

        override fun play() {
            helper?.play()
        }

        override fun getCurrentPlaybackInfo(): PlaybackInfo {
            return helper?.latestPlaybackInfo ?: PlaybackInfo()
        }

        override fun release() {
            helper?.release()
            helper = null
        }

        override fun initialize(container: Container, playbackInfo: PlaybackInfo) {
            if (helper == null) {
                helper = mediaUri?.let { ExoPlayerViewHelper(this, it) };
            }
            helper?.initialize(container, playbackInfo)
        }

        override fun getPlayerOrder(): Int {
            return adapterPosition
        }

    }

}