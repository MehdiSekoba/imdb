package com.mehdisekoba.imdb.data.model.home


import com.google.gson.annotations.SerializedName

data class ResponseUpComing(
    @SerializedName("dates")
    val dates: Dates?,
    @SerializedName("page")
    val page: Int?, // 1
    @SerializedName("results")
    val results: List<Result>?,
    @SerializedName("total_pages")
    val totalPages: Int?, // 42
    @SerializedName("total_results")
    val totalResults: Int? // 838
) {
    data class Dates(
        @SerializedName("maximum")
        val maximum: String?, // 2024-04-17
        @SerializedName("minimum")
        val minimum: String? // 2024-03-27
    )

    data class Result(
        @SerializedName("adult")
        val adult: Boolean?, // false
        @SerializedName("backdrop_path")
        val backdropPath: String?, // /1XDDXPXGiI8id7MrUxK36ke7gkX.jpg
        @SerializedName("genre_ids")
        val genreIds: List<Int?>?,
        @SerializedName("id")
        val id: Int?, // 1011985
        @SerializedName("original_language")
        val originalLanguage: String?, // en
        @SerializedName("original_title")
        val originalTitle: String?, // Kung Fu Panda 4
        @SerializedName("overview")
        val overview: String?, // Po is gearing up to become the spiritual leader of his Valley of Peace, but also needs someone to take his place as Dragon Warrior. As such, he will train a new kung fu practitioner for the spot and will encounter a villain called the Chameleon who conjures villains from the past.
        @SerializedName("popularity")
        val popularity: Double?, // 4814.65
        @SerializedName("poster_path")
        val posterPath: String?, // /wkfG7DaExmcVsGLR4kLouMwxeT5.jpg
        @SerializedName("release_date")
        val releaseDate: String?, // 2024-03-02
        @SerializedName("title")
        val title: String?, // Kung Fu Panda 4
        @SerializedName("video")
        val video: Boolean?, // false
        @SerializedName("vote_average")
        val voteAverage: Double?, // 6.9
        @SerializedName("vote_count")
        val voteCount: Int? // 239
    )
}