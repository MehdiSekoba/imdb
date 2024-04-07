package com.mehdisekoba.imdb.utils.states

import com.mehdisekoba.imdb.data.model.detail.ResponseSimilar

sealed class SearchState : BaseState() {
    data class LoadMoviesSearch(val search: ResponseSimilar) : SearchState()

    data object Empty : BaseState()
}
