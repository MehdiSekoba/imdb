package com.mehdisekoba.imdb.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mehdisekoba.imdb.data.repository.DetailRepository
import com.mehdisekoba.imdb.ui.detail.DetailIntent
import com.mehdisekoba.imdb.utils.network.ErrorResponse
import com.mehdisekoba.imdb.utils.states.BaseState
import com.mehdisekoba.imdb.utils.states.DetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel
    @Inject
    constructor(private val repository: DetailRepository) : ViewModel() {
        val intentChannel = Channel<DetailIntent>()
        private val _state = MutableStateFlow<BaseState>(BaseState.Idle)
        val state: StateFlow<BaseState> get() = _state

        init {
            handleIntents()
        }

        private fun handleIntents() =
            viewModelScope.launch {
                intentChannel.consumeAsFlow().collect { intent ->
                    when (intent) {
                        is DetailIntent.CallMoviesDetail -> fetchingMoviesDetail(intent.id)
                        is DetailIntent.CallSimilarMovies -> fetchingSimilarMovies(intent.id)
                        is DetailIntent.CallPlayVideo -> fetchingPlayVideo(intent.id)
                        is DetailIntent.CallPopularActor -> fetchingPopularActor(intent.id)
                    }
                }
            }

        private fun fetchingMoviesDetail(id: Int) =
            viewModelScope.launch {
                _state.emit(BaseState.Loading)
                val response = repository.getMoviesDetail(id)
                if (response.isSuccessful) {
                    response.body()?.let { DetailState.LoadMoviesDetail(it) }?.let { _state.emit(it) }
                } else {
                    val error = ErrorResponse(response).generalNetworkResponse()
                    _state.emit(error)
                }
            }

        private fun fetchingSimilarMovies(id: Int) =
            viewModelScope.launch {
                _state.emit(BaseState.Loading)
                val response = repository.getSimilarMovies(id)
                if (response.isSuccessful) {
                    response.body()?.let { DetailState.LoadSimilarMovies(it) }?.let { _state.emit(it) }
                } else {
                    val error = ErrorResponse(response).generalNetworkResponse()
                    _state.emit(error)
                }
            }

        private fun fetchingPlayVideo(id: Int) =
            viewModelScope.launch {
                _state.emit(BaseState.Loading)
                val response = repository.getPlayVideo(id)
                if (response.isSuccessful) {
                    response.body()?.let { DetailState.LoadPlayVideo(it) }?.let { _state.emit(it) }
                } else {
                    val error = ErrorResponse(response).generalNetworkResponse()
                    _state.emit(error)
                }
            }

        private fun fetchingPopularActor(id: Int) =
            viewModelScope.launch {
                _state.emit(BaseState.Loading)
                val response = repository.getPopularActor(id)
                if (response.isSuccessful) {
                    response.body()?.let { DetailState.LoadPopularActor(it) }?.let { _state.emit(it) }
                } else {
                    val error = ErrorResponse(response).generalNetworkResponse()
                    _state.emit(error)
                }
            }
    }
