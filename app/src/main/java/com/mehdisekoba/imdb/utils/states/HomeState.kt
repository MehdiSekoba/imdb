package com.mehdisekoba.imdb.utils.states

import com.mehdisekoba.imdb.data.model.home.ResponseMovies
import com.mehdisekoba.imdb.data.model.home.ResponsePopular
import com.mehdisekoba.imdb.data.model.home.ResponseUpComing

sealed class HomeState : BaseState() {
    data class LoadingTopRatedList(val topRated: ResponsePopular) : HomeState()

    data class LoadingPopularList(val popular: ResponsePopular) : HomeState()

    data class LoadingTvList(val tv: ResponseMovies) : HomeState()

    data class LoadingOnTheAirList(val onTheAir: ResponseMovies) : HomeState()

    data class LoadingUpComingList(val upComing: ResponseUpComing) : HomeState()

    data class NavigateToDetail(val id: Int) : HomeState()
}
