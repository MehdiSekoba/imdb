package com.mehdisekoba.imdb.ui.video.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mehdisekoba.imdb.data.model.movie.MovieData
import com.mehdisekoba.imdb.databinding.ItemExploreBinding
import com.mehdisekoba.imdb.utils.base.BaseDiffUtils
import com.mehdisekoba.imdb.utils.extensions.disableWebViewsBackground
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import javax.inject.Inject

class AdapterExplore
    @Inject
    constructor(
        private val lifecycle: Lifecycle,
    ) : RecyclerView.Adapter<AdapterExplore.YouTubePlayerViewHolder>() {
        private lateinit var binding: ItemExploreBinding
        private var items = emptyList<MovieData>()

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int,
        ): AdapterExplore.YouTubePlayerViewHolder {
            binding = ItemExploreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return YouTubePlayerViewHolder()
        }

        override fun onBindViewHolder(
            holder: AdapterExplore.YouTubePlayerViewHolder,
            position: Int,
        ) = holder.bind(items[position])

        override fun getItemCount() = items.size

        inner class YouTubePlayerViewHolder :
            RecyclerView.ViewHolder(binding.root) {
            private var youTubePlayer: YouTubePlayer? = null

            fun bind(item: MovieData) {
                binding.apply {
                    lifecycle.addObserver(youtubePlayerView)
                    overlayView.setOnClickListener { youTubePlayer?.play() }
                    youtubePlayerView.disableWebViewsBackground()
                    youtubePlayerView.addYouTubePlayerListener(
                        object : AbstractYouTubePlayerListener() {
                            override fun onReady(youTubePlayer: YouTubePlayer) {
                                // store youtube player reference for later
                                this@YouTubePlayerViewHolder.youTubePlayer = youTubePlayer
                                item.movieId.let { youTubePlayer.cueVideo(it, 0f) }
                            }

                            override fun onStateChange(
                                youTubePlayer: YouTubePlayer,
                                state: PlayerConstants.PlayerState,
                            ) {
                                when (state) {
                                    PlayerConstants.PlayerState.VIDEO_CUED ->
                                        overlayView.visibility =
                                            View.VISIBLE

                                    else -> overlayView.visibility = View.GONE
                                }
                            }
                        },
                    )
                }
            }
        }

        private var onItemClickListener: ((MovieData) -> Unit)? = null

        fun setOnItemClickListener(listener: (MovieData) -> Unit) {
            onItemClickListener = listener
        }

        fun setData(data: List<MovieData>) {
            val adapterDiffUtils = BaseDiffUtils(items, data)
            val diffUtils = DiffUtil.calculateDiff(adapterDiffUtils)
            items = data
            diffUtils.dispatchUpdatesTo(this)
        }
    }
