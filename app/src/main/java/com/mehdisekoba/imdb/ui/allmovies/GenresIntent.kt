package com.mehdisekoba.imdb.ui.allmovies

sealed class GenresIntent {
    data class CallGenresMovies(val id: Int) : GenresIntent()
}
