package com.mehdisekoba.imdb.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mehdisekoba.imdb.data.repository.GenresRepository
import com.mehdisekoba.imdb.ui.allmovies.GenresIntent
import com.mehdisekoba.imdb.utils.network.ErrorResponse
import com.mehdisekoba.imdb.utils.states.BaseState
import com.mehdisekoba.imdb.utils.states.GenresState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@Suppress("ktlint:standard:backing-property-naming")
class GenresViewModel
    @Inject
    constructor(private val repository: GenresRepository) : ViewModel() {
        val intentChannel = Channel<GenresIntent>()
        private val _state = MutableStateFlow<BaseState>(BaseState.Idle)
        val state: StateFlow<BaseState> get() = _state

        init {
            handleIntents()
        }

        private fun handleIntents() =
            viewModelScope.launch {
                intentChannel.consumeAsFlow().collect { intent ->
                    when (intent) {
                        is GenresIntent.CallGenresMovies -> fetchingGenresMovies(intent.id)
                    }
                }
            }

        private fun fetchingGenresMovies(id: Int) =
            viewModelScope.launch {
                _state.emit(BaseState.Loading)
                val response = repository.moviesWithGenres(id)
                if (response.isSuccessful) {
                    response.body()?.let { GenresState.LoadGenresMovies(it) }?.let {
                        _state.emit(it)
                    }
                } else {
                    val error = ErrorResponse(response).generalNetworkResponse()
                    _state.emit(error)
                }
            }
    }
