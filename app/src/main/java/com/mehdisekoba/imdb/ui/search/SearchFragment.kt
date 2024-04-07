package com.mehdisekoba.imdb.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mehdisekoba.imdb.R
import com.mehdisekoba.imdb.data.model.detail.ResponseSimilar
import com.mehdisekoba.imdb.databinding.FragmentSearchBinding
import com.mehdisekoba.imdb.ui.home.HomeFragmentDirections
import com.mehdisekoba.imdb.ui.search.adapter.SearchAdapter
import com.mehdisekoba.imdb.utils.base.BaseFragment
import com.mehdisekoba.imdb.utils.extensions.setupShimmerRecyclerview
import com.mehdisekoba.imdb.utils.extensions.showSnackBar
import com.mehdisekoba.imdb.utils.states.BaseState
import com.mehdisekoba.imdb.utils.states.SearchState
import com.mehdisekoba.imdb.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>() {
    override val bindingInflater: (inflater: LayoutInflater) -> FragmentSearchBinding
        get() = FragmentSearchBinding::inflate

    @Inject
    lateinit var searchAdapter: SearchAdapter

    // other
    private val viewModel by viewModels<SearchViewModel>()
    private var searchTxt = ""

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        if (isNetworkAvailable) {
            binding.searchView.setOnQueryTextListener(
                object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        return false
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        searchTxt = newText!!
                        if (searchTxt.length > 2) {
                            viewLifecycleOwner.lifecycleScope.launch {
                                repeatOnLifecycle(Lifecycle.State.CREATED) {
                                    viewModel.intentChannel.send(
                                        SearchIntent.CallSearchMovies(
                                            searchTxt,
                                        ),
                                    )
                                }
                            }
                        }
                        return true
                    }
                },
            )
            // Load data
            handleStates()
        } else {
            binding.root.showSnackBar(getString(R.string.checkYourNetwork))
        }
    }

    private fun handleStates() {
        binding.apply {
            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.CREATED) {
                    viewModel.state.collect { state ->
                        when (state) {
                            is BaseState.Idle -> {
                                empty.disTxt.text = getString(R.string.search_movies)
                            }

                            is BaseState.Loading -> {
                                emptyLay.isVisible = false
                            }

                            is BaseState.Error -> {
                                state.error?.let { root.showSnackBar(it) }
                            }

                            is BaseState.Empty -> {
                                emptyLay.isVisible = true
                            }
                            is SearchState.LoadMoviesSearch -> {
                                initSearchList(state.search)
                            }

                            else -> {}
                        }
                    }
                }
            }
        }
    }

    private fun initSearchList(search: ResponseSimilar) {
        if (search.results!!.isNotEmpty()) {
            searchAdapter.setData(search.results)
        }
        binding.searchList.setupShimmerRecyclerview(
            LinearLayoutManager(requireContext()),
            searchAdapter,
        )
        searchAdapter.setOnItemClickListener {
            val direction = HomeFragmentDirections.actionToDetail(it.id!!.toString())
            findNavController().navigate(direction)
        }
    }
}
