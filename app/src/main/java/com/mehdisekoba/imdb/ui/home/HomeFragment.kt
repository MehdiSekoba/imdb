@file:Suppress("ktlint:standard:no-wildcard-imports")

package com.mehdisekoba.imdb.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.carousel.CarouselLayoutManager
import com.google.android.material.carousel.HeroCarouselStrategy
import com.mehdisekoba.imdb.R
import com.mehdisekoba.imdb.data.model.home.ResponseMovies
import com.mehdisekoba.imdb.data.model.home.ResponsePopular
import com.mehdisekoba.imdb.data.model.home.ResponseUpComing
import com.mehdisekoba.imdb.databinding.FragmentHomeBinding
import com.mehdisekoba.imdb.ui.home.adapter.AdapterBanner
import com.mehdisekoba.imdb.ui.home.adapter.AdapterPopular
import com.mehdisekoba.imdb.ui.home.adapter.AdapterTvOnTheAir
import com.mehdisekoba.imdb.ui.home.adapter.AdapterTvVideo
import com.mehdisekoba.imdb.ui.home.adapter.AdapterUpComing
import com.mehdisekoba.imdb.ui.home.intent.HomeIntent
import com.mehdisekoba.imdb.utils.*
import com.mehdisekoba.imdb.utils.base.BaseFragment
import com.mehdisekoba.imdb.utils.extensions.setupShimmerRecyclerview
import com.mehdisekoba.imdb.utils.extensions.showSnackBar
import com.mehdisekoba.imdb.utils.states.BaseState
import com.mehdisekoba.imdb.utils.states.HomeState
import com.mehdisekoba.imdb.viewmodel.HomeViewModel
import com.mikelau.shimmerrecyclerviewx.ShimmerRecyclerViewX
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    override val bindingInflater: (inflater: LayoutInflater) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    @Inject
    lateinit var adapterBanner: AdapterBanner

    @Inject
    lateinit var adapterPopular: AdapterPopular

    @Inject
    lateinit var adapterTvVideo: AdapterTvVideo

    @Inject
    lateinit var adapterTvOnTheAir: AdapterTvOnTheAir

    @Inject
    lateinit var adapterUpComing: AdapterUpComing

    // other
    private val viewModel by viewModels<HomeViewModel>()
    private var autoScrollIndex = 0

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                if (isNetworkAvailable) {
                    viewModel.intentChannel.send(HomeIntent.CallTopRatedList)
                    viewModel.intentChannel.send(HomeIntent.CallPopularVideo)
                    viewModel.intentChannel.send(HomeIntent.CallTvList)
                    viewModel.intentChannel.send(HomeIntent.CallOneTheAirList)
                    viewModel.intentChannel.send(HomeIntent.CallUpComingList)
                    // Load data
                    handleStates()
                } else {
                    binding.root.showSnackBar(getString(R.string.checkYourNetwork))
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
                            is BaseState.Loading -> {
                                setupLoadingHome(
                                    true,
                                    bannerList,
                                    popularList,
                                    tvList,
                                    tvAirList,
                                    UpComingList,
                                )
                            }

                            is BaseState.Error -> {
                                setupLoadingHome(
                                    false,
                                    bannerList,
                                    popularList,
                                    tvList,
                                    tvAirList,
                                    UpComingList,
                                )

                                state.error?.let {
                                    root.showSnackBar(it)
                                }
                            }

                            is HomeState.LoadingTopRatedList -> initBannerList(state.topRated)

                            is HomeState.LoadingPopularList -> initPopularList(state.popular)
                            is HomeState.LoadingTvList -> initTvList(state.tv)
                            is HomeState.LoadingOnTheAirList -> initOnTheAirList(state.onTheAir)
                            is HomeState.LoadingUpComingList -> initUpComingList(state.upComing)
                            is HomeState.NavigateToDetail -> navigateToDetail(state.id)
                            else -> {}
                        }
                    }
                }
            }
        }
    }

    private fun initBannerList(topRated: ResponsePopular) {
        adapterBanner.setData(topRated.results!!)
        autoScrollPopular(adapterBanner)
        binding.bannerList.setupShimmerRecyclerview(
            CarouselLayoutManager(HeroCarouselStrategy()),
            adapterBanner,
        )
        CarouselLayoutManager.HORIZONTAL
        adapterBanner.setOnItemClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.CREATED) {
                    viewModel.intentChannel.send(HomeIntent.NavigateToDetail(it.id!!.toString()))
                }
            }
        }
    }

    private fun autoScrollPopular(adapter: AdapterBanner) {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                repeat(REPEAT_TIME) {
                    delay(DELAY_TIME)
                    val itemCount = adapter.itemCount
                    if (autoScrollIndex < itemCount) {
                        autoScrollIndex += 1
                    } else {
                        autoScrollIndex = 0
                    }
                    binding.bannerList.smoothScrollToPosition(autoScrollIndex)
                }
            }
        }
    }

    private fun initPopularList(popular: ResponsePopular) {
        binding.apply {
            if (popular.results!!.isNotEmpty()) {
                ITEM_COUNT =
                    if (popular.results.size < VIDEO_COUNT) {
                        popular.results.size
                    } else {
                        VIDEO_COUNT
                    }
                adapterPopular.setData(popular.results)
                popularList.setupShimmerRecyclerview(
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false),
                    adapterPopular,
                )
                adapterPopular.setOnItemClickListener {
                    viewLifecycleOwner.lifecycleScope.launch {
                        repeatOnLifecycle(Lifecycle.State.CREATED) {
                            viewModel.intentChannel.send(HomeIntent.NavigateToDetail(it.id!!.toString()))
                        }
                    }
                }
            }
            // see all
            seeAllPop.setOnClickListener {
                val direction =
                    HomeFragmentDirections.actionHomeToAllMovies(
                        Crime.toString(),
                        POPULAR,
                    )
                findNavController().navigate(direction)
            }
        }
    }

    private fun initTvList(tv: ResponseMovies) {
        binding.apply {
            if (tv.results!!.isNotEmpty()) {
                ITEM_COUNT =
                    if (tv.results.size < VIDEO_COUNT) {
                        tv.results.size
                    } else {
                        VIDEO_COUNT
                    }
                adapterTvVideo.setData(tv.results)
                tvList.setupShimmerRecyclerview(
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false),
                    adapterTvVideo,
                )
                adapterTvVideo.setOnItemClickListener {
                    viewLifecycleOwner.lifecycleScope.launch {
                        repeatOnLifecycle(Lifecycle.State.CREATED) {
                            viewModel.intentChannel.send(HomeIntent.NavigateToDetail(it.id!!.toString()))
                        }
                    }
                }
            }
            seeAllTv.setOnClickListener {
                val direction =
                    HomeFragmentDirections.actionHomeToAllMovies(TV_Movie.toString(), TV)
                findNavController().navigate(direction)
            }
        }
    }

    private fun initOnTheAirList(onTheAir: ResponseMovies) {
        binding.apply {
            if (onTheAir.results!!.isNotEmpty()) {
                ITEM_COUNT =
                    if (onTheAir.results.size < VIDEO_COUNT) {
                        onTheAir.results.size
                    } else {
                        VIDEO_COUNT
                    }
                adapterTvOnTheAir.setData(onTheAir.results)
                tvAirList.setupShimmerRecyclerview(
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false),
                    adapterTvOnTheAir,
                )
                adapterTvOnTheAir.setOnItemClickListener {
                    viewLifecycleOwner.lifecycleScope.launch {
                        repeatOnLifecycle(Lifecycle.State.CREATED) {
                            viewModel.intentChannel.send(
                                HomeIntent.NavigateToDetail(it.id!!.toString()),
                            )
                        }
                    }
                }
            }
            // click
            seeAllAir.setOnClickListener {
                val direction =
                    HomeFragmentDirections.actionHomeToAllMovies(
                        Drama.toString(),
                        TV_ON_THE_AIR,
                    )
                findNavController().navigate(direction)
            }
        }
    }

    private fun initUpComingList(upComing: ResponseUpComing) {
        binding.apply {
            if (upComing.results!!.isNotEmpty()) {
                ITEM_COUNT =
                    if (upComing.results.size < VIDEO_COUNT) {
                        upComing.results.size
                    } else {
                        VIDEO_COUNT
                    }
                adapterUpComing.setData(upComing.results)
                binding.UpComingList.setupShimmerRecyclerview(
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false),
                    adapterUpComing,
                )
                adapterUpComing.setOnItemClickListener {
                    viewLifecycleOwner.lifecycleScope.launch {
                        repeatOnLifecycle(Lifecycle.State.CREATED) {
                            viewModel.intentChannel.send(HomeIntent.NavigateToDetail(it.id!!.toString()))
                        }
                    }
                }
            }

            // click
            seeAllUpcoming.setOnClickListener {
                val direction =
                    HomeFragmentDirections.actionHomeToAllMovies(
                        Action.toString(),
                        UPCOMING,
                    )
                findNavController().navigate(direction)
            }
        }
    }

    private fun navigateToDetail(id: Int) {
        val direction = HomeFragmentDirections.actionToDetail(id.toString())
        findNavController().navigate(direction)
    }

    fun setupLoadingHome(
        isShownLoading: Boolean,
        vararg shimmer: ShimmerRecyclerViewX,
    ) {
        shimmer.forEach {
            if (isShownLoading) it.showShimmerAdapter() else it.hideShimmerAdapter()
        }
    }
}
