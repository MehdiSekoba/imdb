package com.mehdisekoba.imdb.utils.states

import com.mehdisekoba.imdb.data.model.detail.ResponseActor
import com.mehdisekoba.imdb.data.model.detail.ResponseDetail
import com.mehdisekoba.imdb.data.model.detail.ResponsePlayerVideo
import com.mehdisekoba.imdb.data.model.detail.ResponseSimilar

sealed class DetailState : BaseState() {
    data class LoadMoviesDetail(val detail: ResponseDetail) : DetailState()

    data class LoadSimilarMovies(val similar: ResponseSimilar) : DetailState()

    data class LoadPlayVideo(val video: ResponsePlayerVideo) : DetailState()

    data class LoadPopularActor(val actor: ResponseActor) : DetailState()
}
