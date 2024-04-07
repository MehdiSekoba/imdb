package com.mehdisekoba.imdb.data.model.onboarding

import com.mehdisekoba.imdb.R

data class IntroSlide(
    val id: Int,
    val title: String,
    val description: String,
    val iconResourceId: Int,
)

val dataLocal =
    listOf(
        IntroSlide(
            id = 1,
            "Rating",
            "Check the rating of your video show & movies",
            R.raw.rating,
        ),
        IntroSlide(
            id = 2,
            "Watchlist",
            "Add movies and Tv show to your Watchlist",
            R.raw.bookmark,
        ),
        IntroSlide(
            id = 3,
            "Trailers",
            "Check the rating of your video show & movies",
            R.raw.trailer_movies,
        ),
    )
