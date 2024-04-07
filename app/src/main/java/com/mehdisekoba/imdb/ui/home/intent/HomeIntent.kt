package com.mehdisekoba.imdb.ui.home.intent

sealed class HomeIntent {
    data object CallTopRatedList : HomeIntent()

    data object CallPopularVideo : HomeIntent()

    data object CallTvList : HomeIntent()

    data object CallOneTheAirList : HomeIntent()

    data object CallUpComingList : HomeIntent()

    data class NavigateToDetail(val id: String) : HomeIntent()
}
