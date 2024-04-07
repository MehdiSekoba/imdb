package com.mehdisekoba.imdb.data.repository

import com.mehdisekoba.imdb.data.network.ApiServices
import javax.inject.Inject

class DetailRepository
    @Inject
    constructor(private val api: ApiServices) {
        suspend fun getMoviesDetail(id: Int) = api.getMoviesDetail(id)

        suspend fun getSimilarMovies(id: Int) = api.getSimilarMovies(id)

        suspend fun getPlayVideo(id: Int) = api.getPlayVideo(id)

        suspend fun getPopularActor(id: Int) = api.getPopularActor(id)
    }
