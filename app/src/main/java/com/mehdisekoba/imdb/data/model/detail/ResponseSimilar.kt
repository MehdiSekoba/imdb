package com.mehdisekoba.imdb.data.model.detail

import com.google.gson.annotations.SerializedName

@Suppress("ktlint:standard:max-line-length")
data class ResponseSimilar(
    @SerializedName("page")
    val page: Int?, // 1
    @SerializedName("results")
    val results: List<Result>?,
    @SerializedName("total_pages")
    val totalPages: Int?, // 2344
    @SerializedName("total_results")
    val totalResults: Int?, // 46862
) {
    data class Result(
        @SerializedName("adult")
        val adult: Boolean?, // false
        @SerializedName("backdrop_path")
        val backdropPath: String?, // /5kCwJfMh0WTQ6AGLsuyzeiGLWOd.jpg
        @SerializedName("genre_ids")
        val genreIds: List<Int?>?,
        @SerializedName("id")
        val id: Int?, // 215760
        @SerializedName("original_language")
        val originalLanguage: String?, // en
        @SerializedName("original_title")
        val originalTitle: String?, // New Eden
        @SerializedName("overview")
        val overview: String?, // Prisoners are dumped on a sand planet dubbed Earth 21-523 where most are immediately killed by the sand people and the remainder struggle for existence. That is until a new prisoner arrives with ideas of providing irrigation of the desert. But he still must first fight the nomadic sand people.
        @SerializedName("popularity")
        val popularity: Double?, // 5.249
        @SerializedName("poster_path")
        val posterPath: String?, // /ju6gadJFQT2JwuzWYwu8B1ya6zM.jpg
        @SerializedName("release_date")
        val releaseDate: String?, // 1994-11-28
        @SerializedName("title")
        val title: String?, // New Eden
        @SerializedName("video")
        val video: Boolean?, // false
        @SerializedName("vote_average")
        val voteAverage: Double?, // 4.2
        @SerializedName("vote_count")
        val voteCount: Int?, // 10
    )
}
