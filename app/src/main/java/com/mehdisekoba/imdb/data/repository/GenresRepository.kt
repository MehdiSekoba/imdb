package com.mehdisekoba.imdb.data.repository

import com.mehdisekoba.imdb.data.network.ApiServices
import javax.inject.Inject

class GenresRepository
    @Inject
    constructor(private val api: ApiServices) {
        suspend fun moviesWithGenres(id: Int) = api.moviesWithGenres(id)
    }
