package com.mehdisekoba.imdb.data.model.home

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Suppress("ktlint:standard:max-line-length")
@Parcelize
data class ResponsePopular(
    @SerializedName("page")
    val page: Int?, // 1
    @SerializedName("results")
    val results: @RawValue List<Result>?,
    @SerializedName("total_pages")
    val totalPages: Int?, // 43103
    @SerializedName("total_results")
    val totalResults: Int?, // 862053
) : Parcelable {
    data class Result(
        @SerializedName("adult")
        val adult: Boolean?, // false
        @SerializedName("backdrop_path")
        val backdropPath: String?, // /sJA8Nnnj547WTFwqHYNu0Y8BxHM.jpg
        @SerializedName("genre_ids")
        val genreIds: List<Int>?,
        @SerializedName("id")
        val id: Int?, // 1011985
        @SerializedName("original_language")
        val originalLanguage: String?, // en
        @SerializedName("original_title")
        val originalTitle: String?, // Kung Fu Panda 4
        @SerializedName("overview")
        val overview: String?, // Po is gearing up to become the spiritual leader of his Valley of Peace, but also needs someone to take his place as Dragon Warrior. As such, he will train a new kung fu practitioner for the spot and will encounter a villain called the Chameleon who conjures villains from the past.
        @SerializedName("popularity")
        val popularity: Double?, // 5294.537
        @SerializedName("poster_path")
        val posterPath: String?, // /wkfG7DaExmcVsGLR4kLouMwxeT5.jpg
        @SerializedName("release_date")
        val releaseDate: String?, // 2024-03-02
        @SerializedName("title")
        val title: String?, // Kung Fu Panda 4
        @SerializedName("video")
        val video: Boolean?, // false
        @SerializedName("vote_average")
        val voteAverage: Double?, // 6.978
        @SerializedName("vote_count")
        val voteCount: Int?, // 182
    )
}
