package com.mehdisekoba.imdb.ui.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.mehdisekoba.imdb.R
import com.mehdisekoba.imdb.data.model.detail.ResponseActor
import com.mehdisekoba.imdb.data.model.detail.ResponseDetail
import com.mehdisekoba.imdb.data.model.detail.ResponseSimilar
import com.mehdisekoba.imdb.databinding.FragmentDetailMoviesBinding
import com.mehdisekoba.imdb.ui.detail.adapter.AdapterActor
import com.mehdisekoba.imdb.ui.detail.adapter.AdapterGenre
import com.mehdisekoba.imdb.ui.detail.adapter.AdapterSimilar
import com.mehdisekoba.imdb.ui.home.HomeFragmentDirections
import com.mehdisekoba.imdb.utils.ITEM_COUNT
import com.mehdisekoba.imdb.utils.SIMILAR_COUNT
import com.mehdisekoba.imdb.utils.TMDB_IMAGE_BASE_URL_W780
import com.mehdisekoba.imdb.utils.base.BaseFragment
import com.mehdisekoba.imdb.utils.extensions.loadImageCircularProgress
import com.mehdisekoba.imdb.utils.extensions.setupShimmerRecyclerview
import com.mehdisekoba.imdb.utils.extensions.showSnackBar
import com.mehdisekoba.imdb.utils.extensions.toHoursAndMinutesString
import com.mehdisekoba.imdb.utils.extensions.toTwoDecimals
import com.mehdisekoba.imdb.utils.states.BaseState
import com.mehdisekoba.imdb.utils.states.DetailState
import com.mehdisekoba.imdb.viewmodel.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class DetailMoviesFragment : BaseFragment<FragmentDetailMoviesBinding>() {
    override val bindingInflater: (inflater: LayoutInflater) -> FragmentDetailMoviesBinding
        get() = FragmentDetailMoviesBinding::inflate

    @Inject
    lateinit var adapterGenre: AdapterGenre

    @Inject
    lateinit var adapterSimilar: AdapterSimilar

    @Inject
    lateinit var adapterActor: AdapterActor

    // Other
    private val viewModel by viewModels<DetailViewModel>()
    private val args by navArgs<DetailMoviesFragmentArgs>()
    private var itemId = 0

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        // Args
        args.let {
            if (it.id.isNotEmpty()) {
                itemId = it.id.toInt()
                // Call api
                viewLifecycleOwner.lifecycleScope.launch {
                    repeatOnLifecycle(Lifecycle.State.CREATED) {
                        if (isNetworkAvailable) {
                            viewModel.intentChannel.send(DetailIntent.CallMoviesDetail(itemId))
                            viewModel.intentChannel.send(DetailIntent.CallSimilarMovies(itemId))
                            viewModel.intentChannel.send(DetailIntent.CallPopularActor(itemId))
                            // Load data
                            handleStates()
                        } else {
                            binding.root.showSnackBar(getString(R.string.checkYourNetwork))
                        }
                    }
                }
            }
        }

        binding.apply {
            // Back
            imgBack.setOnClickListener {
                findNavController().popBackStack(
                    R.id.homeFragment,
                    false,
                )
            }
            // player
            imgPlayer.setOnClickListener {
                val direction =
                    HomeFragmentDirections.actionVideoPlayerToVideoFragment(itemId.toString())
                findNavController().navigate(direction)
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
                                delay(700)
                                listOf(actorList, txtActor, similarList, txtSimilar).onEach { it.visibility = View.GONE }
                            }
                            is DetailState.LoadMoviesDetail -> initDetailViews(state.detail)
                            is DetailState.LoadSimilarMovies -> initDetailSimilar(state.similar)
                            is DetailState.LoadPopularActor -> initDetailActor(state.actor)
                            else -> {}
                        }
                    }
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initDetailViews(detail: ResponseDetail) {
        binding.apply {
            detail.let {
                imgPoster.loadImageCircularProgress(TMDB_IMAGE_BASE_URL_W780 + it.posterPath!!)
                txtTitle.text = it.title
                txtTimeDate.text =
                    "${it.releaseDate}  -  ${it.runtime.toHoursAndMinutesString()}"
                txtRating.text = it.voteAverage!!.toTwoDecimals()
                txtOverview.text = it.overview
                adapterGenre.setData(it.genres!!)
                GenreList.setupShimmerRecyclerview(
                    LinearLayoutManager(
                        requireContext(),
                        LinearLayoutManager.HORIZONTAL,
                        false,
                    ),
                    adapterGenre,
                )
            }
        }
    }

    private fun initDetailSimilar(detail: ResponseSimilar) {
        if (detail.results!!.isNotEmpty()) {
            ITEM_COUNT =
                if (detail.results.size < SIMILAR_COUNT) {
                    detail.results.size
                } else {
                    SIMILAR_COUNT
                }
            binding.apply {
                adapterSimilar.setData(detail.results)
                similarList.setupShimmerRecyclerview(
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false),
                    adapterSimilar,
                )
                adapterSimilar.setOnItemClickListener {
                    val action = HomeFragmentDirections.actionToDetail(it.id.toString())
                    findNavController().navigate(action)
                }
            }
        }
    }

    private fun initDetailActor(actor: ResponseActor) {
        binding.apply {
            adapterActor.setData(actor.cast!!)
            actorList.setupShimmerRecyclerview(
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false),
                adapterActor,
            )
        }
    }
}
