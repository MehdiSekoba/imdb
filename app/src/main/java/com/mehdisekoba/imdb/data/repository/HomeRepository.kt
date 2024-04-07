package com.mehdisekoba.imdb.data.repository

import com.mehdisekoba.imdb.data.network.ApiServices
import com.mehdisekoba.imdb.utils.DEFAULT_PAGE
import javax.inject.Inject

class HomeRepository
    @Inject
    constructor(private val api: ApiServices) {
        suspend fun getPopularList() = api.getPopularList(DEFAULT_PAGE)

        suspend fun getTopRatedList() = api.getTopRatedList()

        suspend fun getTvList() = api.getTvList()

        suspend fun getOneTheAirList() = api.getOnTheAir()

        suspend fun getUpComing() = api.getUpComing()
    }
