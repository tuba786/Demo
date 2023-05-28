package com.digipod.demo

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.VideoView
import androidx.recyclerview.widget.RecyclerView

class VideoAdapter : RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {
    private var videoList: List<String> = emptyList()

    fun setVideos(videos: List<String>) {
        videoList = videos
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_video, parent, false)
        return VideoViewHolder(view)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val videoPath = videoList[position]
        holder.bind(videoPath)
    }

    override fun getItemCount(): Int {
        return videoList.size
    }

    inner class VideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val videoView: VideoView = itemView.findViewById(R.id.videoView)
        private val playPauseImageView: ImageView = itemView.findViewById(R.id.playPauseImageView)
        private var isVideoPlaying: Boolean = false

        init {
            videoView.setOnPreparedListener { mediaPlayer ->
                mediaPlayer.isLooping = true
                if (isVideoPlaying) {
                    mediaPlayer.start()
                }
            }

            itemView.setOnClickListener {
                if (isVideoPlaying) {
                    pauseVideo()
                } else {
                    playVideo()
                }
                updatePlayPauseIcon()
            }
        }

        fun bind(videoPath: String) {
            videoView.setVideoURI(Uri.parse(videoPath))
        }

        private fun playVideo() {
            isVideoPlaying = true
            videoView.start()
        }

        private fun pauseVideo() {
            isVideoPlaying = false
            videoView.pause()
        }
        private fun updatePlayPauseIcon() {
            if (isVideoPlaying) {
                playPauseImageView.setImageResource(R.drawable.ic_pause)
            } else {
                playPauseImageView.setImageResource(R.drawable.ic_play)
            }
        }
    }

}
