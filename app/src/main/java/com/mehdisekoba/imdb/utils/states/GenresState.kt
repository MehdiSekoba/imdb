package com.mehdisekoba.imdb.utils.states

import com.mehdisekoba.imdb.data.model.detail.ResponseSimilar

sealed class GenresState : BaseState() {
    data class LoadGenresMovies(val genres: ResponseSimilar) : GenresState()
}
