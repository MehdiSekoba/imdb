package com.mehdisekoba.imdb.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mehdisekoba.imdb.data.repository.HomeRepository
import com.mehdisekoba.imdb.ui.home.intent.HomeIntent
import com.mehdisekoba.imdb.utils.network.ErrorResponse
import com.mehdisekoba.imdb.utils.states.BaseState
import com.mehdisekoba.imdb.utils.states.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@Suppress("ktlint:standard:backing-property-naming")
class HomeViewModel
    @Inject
    constructor(private val repository: HomeRepository) : ViewModel() {
        val intentChannel = Channel<HomeIntent>()
        private val _state = MutableStateFlow<BaseState>(BaseState.Idle)
        val state: StateFlow<BaseState> get() = _state

        init {
            handleIntents()
        }

        private fun handleIntents() =
            viewModelScope.launch {
                intentChannel.consumeAsFlow().collect { intent ->
                    when (intent) {
                        is HomeIntent.CallTopRatedList -> fetchingTopRatedList()
                        is HomeIntent.CallPopularVideo -> fetchingPopularList()
                        is HomeIntent.CallTvList -> fetchingTvList()
                        is HomeIntent.CallOneTheAirList -> fetchingOnTheAirList()
                        is HomeIntent.CallUpComingList -> fetchingUpComingList()
                        is HomeIntent.NavigateToDetail -> navigateToDetail(intent.id)
                    }
                }
            }

        private fun fetchingTopRatedList() =
            viewModelScope.launch {
                _state.emit(BaseState.Loading)
                val response = repository.getTopRatedList()
                if (response.isSuccessful) {
                    response.body()?.let { HomeState.LoadingTopRatedList(it) }?.let {
                        _state.emit(it)
                    }
                } else {
                    val error = ErrorResponse(response).generalNetworkResponse()
                    _state.emit(error)
                }
            }

        private fun fetchingPopularList() =
            viewModelScope.launch {
                _state.emit(BaseState.Loading)
                val response = repository.getPopularList()
                if (response.isSuccessful) {
                    response.body()?.let { HomeState.LoadingPopularList(it) }?.let {
                        _state.emit(it)
                    }
                } else {
                    val error = ErrorResponse(response).generalNetworkResponse()
                    _state.emit(error)
                }
            }

        private fun fetchingTvList() =
            viewModelScope.launch {
                _state.emit(BaseState.Loading)
                val response = repository.getTvList()
                if (response.isSuccessful) {
                    response.body()?.let { HomeState.LoadingTvList(it) }?.let { _state.emit(it) }
                } else {
                    val error = ErrorResponse(response).generalNetworkResponse()
                    _state.emit(error)
                }
            }

        private fun fetchingOnTheAirList() =
            viewModelScope.launch {
                _state.emit(BaseState.Loading)
                val response = repository.getOneTheAirList()
                if (response.isSuccessful) {
                    response.body()?.let { HomeState.LoadingOnTheAirList(it) }?.let { _state.emit(it) }
                } else {
                    val error = ErrorResponse(response).generalNetworkResponse()
                    _state.emit(error)
                }
            }

        private fun fetchingUpComingList() =
            viewModelScope.launch {
                _state.emit(BaseState.Loading)
                val response = repository.getUpComing()
                if (response.isSuccessful) {
                    response.body()?.let { HomeState.LoadingUpComingList(it) }?.let { _state.emit(it) }
                } else {
                    val error = ErrorResponse(response).generalNetworkResponse()
                    _state.emit(error)
                }
            }

        private fun navigateToDetail(id: String) =
            viewModelScope.launch {
                _state.emit(HomeState.NavigateToDetail(id.toInt()))
            }
    }
