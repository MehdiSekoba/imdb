package com.mehdisekoba.imdb.ui.player

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mehdisekoba.imdb.R
import com.mehdisekoba.imdb.data.model.detail.ResponsePlayerVideo
import com.mehdisekoba.imdb.databinding.FragmentVideoPlayerBinding
import com.mehdisekoba.imdb.ui.detail.DetailIntent
import com.mehdisekoba.imdb.ui.home.HomeFragmentDirections
import com.mehdisekoba.imdb.utils.base.BaseFragment
import com.mehdisekoba.imdb.utils.extensions.showSnackBar
import com.mehdisekoba.imdb.utils.states.BaseState
import com.mehdisekoba.imdb.utils.states.DetailState
import com.mehdisekoba.imdb.viewmodel.DetailViewModel
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@Suppress("ktlint:standard:no-consecutive-blank-lines")
@AndroidEntryPoint
class VideoPlayerFragment : BaseFragment<FragmentVideoPlayerBinding>() {
    override val bindingInflater: (inflater: LayoutInflater) -> FragmentVideoPlayerBinding
        get() = FragmentVideoPlayerBinding::inflate

    // other
    private val args by navArgs<VideoPlayerFragmentArgs>()
    private val viewModel by viewModels<DetailViewModel>()
    private var itemId = 0
    private lateinit var youTubePlayer: YouTubePlayer
    private var isFullscreen = false
    private val onBackPressedCallback =
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (isFullscreen) {
                    youTubePlayer.toggleFullscreen()
                } else {
                    val direction = HomeFragmentDirections.actionToDetail(itemId.toString())
                    findNavController().navigate(direction)
                }
            }
        }


    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(onBackPressedCallback)

        args.let {
            if (it.id.isNotEmpty()) {
                itemId = it.id.toInt()
                // Call api
                viewLifecycleOwner.lifecycleScope.launch {
                    repeatOnLifecycle(Lifecycle.State.CREATED) {
                        if (isNetworkAvailable) {
                            viewModel.intentChannel.send(DetailIntent.CallPlayVideo(itemId))
                            // Load data
                            handleStates()
                        } else {
                            binding.root.showSnackBar(getString(R.string.checkYourNetwork))
                        }
                    }
                }
            }
        }
    }


    private fun handleStates() {
        binding.apply {
            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.CREATED) {
                    viewModel.state.collect { state ->
                        when (state) {
                            is BaseState.Idle -> {}
                            is BaseState.Loading -> {}
                            is BaseState.Error -> {
                                state.error?.let { root.showSnackBar(it) }
                            }

                            is DetailState.LoadPlayVideo -> initPlayerVideo(state.video)
                            else -> {}
                        }
                    }
                }
            }
        }
    }

    private fun initPlayerVideo(video: ResponsePlayerVideo) {
        video.results?.firstOrNull()?.key?.let { firstVideoId ->
            with(binding) {
                val iFramePlayerOptions =
                    IFramePlayerOptions.Builder()
                        .controls(1)
                        .fullscreen(1) // enable full screen button
                        .autoplay(1)
                        .build()
                youtubePlayerView.enableAutomaticInitialization = false
                youtubePlayerView.initialize(
                    object : AbstractYouTubePlayerListener() {
                        override fun onReady(youTubePlayer: YouTubePlayer) {
                            this@VideoPlayerFragment.youTubePlayer = youTubePlayer
                            youTubePlayer.loadVideo(firstVideoId, 0f)
                        }
                    },
                    iFramePlayerOptions,
                )

                lifecycle.addObserver(youtubePlayerView)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
    }

    override fun onStop() {
        super.onStop()
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
    }
}
