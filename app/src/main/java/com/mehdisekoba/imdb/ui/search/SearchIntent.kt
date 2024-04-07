package com.mehdisekoba.imdb.ui.search

sealed class SearchIntent {
    data class CallSearchMovies(val keyWord: String) : SearchIntent()
}
