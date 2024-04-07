package com.mehdisekoba.imdb.ui.allmovies

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
import com.mehdisekoba.imdb.data.model.detail.ResponseSimilar
import com.mehdisekoba.imdb.databinding.FragmentAllMoviesBinding
import com.mehdisekoba.imdb.ui.allmovies.adapter.AdapterGenresMovies
import com.mehdisekoba.imdb.utils.base.BaseFragment
import com.mehdisekoba.imdb.utils.extensions.setupLoading
import com.mehdisekoba.imdb.utils.extensions.setupShimmerRecyclerview
import com.mehdisekoba.imdb.utils.extensions.showSnackBar
import com.mehdisekoba.imdb.utils.states.BaseState
import com.mehdisekoba.imdb.utils.states.GenresState
import com.mehdisekoba.imdb.viewmodel.GenresViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AllMoviesFragment : BaseFragment<FragmentAllMoviesBinding>() {
    override val bindingInflater: (inflater: LayoutInflater) -> FragmentAllMoviesBinding
        get() = FragmentAllMoviesBinding::inflate

    @Inject
    lateinit var adapterGenresMovies: AdapterGenresMovies

    // Other
    private val viewModel by viewModels<GenresViewModel>()
    private val args by navArgs<AllMoviesFragmentArgs>()
    private var itemId = 0

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            // Args
            args.let {
                if (it.id.isNotEmpty()) {
                    itemId = it.id.toInt()
                    txtTitle.text = it.title
                    // Call api
                    viewLifecycleOwner.lifecycleScope.launch {
                        repeatOnLifecycle(Lifecycle.State.CREATED) {
                            if (isNetworkAvailable) {
                                viewModel.intentChannel.send(GenresIntent.CallGenresMovies(itemId))
                                // Load data
                                handleStates()
                            } else {
                                root.showSnackBar(getString(R.string.checkYourNetwork))
                            }
                        }
                    }
                }
            }
            // back
            imgBack.setOnClickListener { findNavController().popBackStack() }
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
                                setupLoading(true, moviesList)
                            }

                            is BaseState.Error -> {
                                state.error?.let {
                                    root.showSnackBar(it)
                                    setupLoading(false, moviesList)
                                }
                            }

                            is GenresState.LoadGenresMovies -> initGenresViews(state.genres)
                            else -> {}
                        }
                    }
                }
            }
        }
    }

    private fun initGenresViews(genres: ResponseSimilar) {
        adapterGenresMovies.setData(genres.results!!)
        binding.moviesList.setupShimmerRecyclerview(
            LinearLayoutManager(requireContext()),
            adapterGenresMovies,
        )
        adapterGenresMovies.setOnItemClickListener {
            val direction = AllMoviesFragmentDirections.actionToDetail(it.id.toString())
            findNavController().navigate(direction)
        }
    }
}
