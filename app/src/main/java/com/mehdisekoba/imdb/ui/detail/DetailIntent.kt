package com.mehdisekoba.imdb.ui.detail

sealed class DetailIntent {
    data class CallMoviesDetail(val id: Int) : DetailIntent()

    data class CallSimilarMovies(val id: Int) : DetailIntent()

    data class CallPlayVideo(val id: Int) : DetailIntent()

    data class CallPopularActor(val id: Int) : DetailIntent()
}
