package com.mehdisekoba.imdb.data.repository

import com.mehdisekoba.imdb.data.network.ApiServices
import javax.inject.Inject

class SearchRepository
    @Inject
    constructor(private val api: ApiServices) {
        suspend fun getSearchMovies(keyWord: String) = api.searchByKeyword(keyWord)
    }
