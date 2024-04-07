package com.mehdisekoba.imdb.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mehdisekoba.imdb.data.repository.SearchRepository
import com.mehdisekoba.imdb.ui.search.SearchIntent
import com.mehdisekoba.imdb.utils.network.ErrorResponse
import com.mehdisekoba.imdb.utils.states.BaseState
import com.mehdisekoba.imdb.utils.states.SearchState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@Suppress("ktlint:standard:backing-property-naming")
class SearchViewModel
    @Inject
    constructor(private val repository: SearchRepository) : ViewModel() {
        val intentChannel = Channel<SearchIntent>()
        private val _state = MutableStateFlow<BaseState>(BaseState.Idle)
        val state: StateFlow<BaseState> get() = _state

        init {
            handleIntents()
        }

        private fun handleIntents() =
            viewModelScope.launch {
                intentChannel.consumeAsFlow().collect { intent ->
                    when (intent) {
                        is SearchIntent.CallSearchMovies -> fetchingSearchMovies(intent.keyWord)
                    }
                }
            }

        private fun fetchingSearchMovies(keyWord: String) =
            viewModelScope.launch {
                _state.emit(BaseState.Loading)
                val response = repository.getSearchMovies(keyWord)
                if (response.isSuccessful) {
                    response.body()?.let {
                        _state.value =
                            if (response.body()!!.results != null) {
                                SearchState.LoadMoviesSearch(it)
                            } else {
                                SearchState.Empty
                            }
                    }
                } else {
                    val error = ErrorResponse(response).generalNetworkResponse()
                    _state.emit(error)
                }
            }
    }
