package com.mehdisekoba.imdb.data.model.home

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Suppress("ktlint:standard:max-line-length")
@Parcelize
data class ResponseMovies(
    @SerializedName("page")
    val page: Int?, // 1
    @SerializedName("results")
    val results: @RawValue List<Result>?,
    @SerializedName("total_pages")
    val totalPages: Int?, // 57
    @SerializedName("total_results")
    val totalResults: Int?, // 1122
) : Parcelable {
    data class Result(
        @SerializedName("adult")
        val adult: Boolean?, // false
        @SerializedName("backdrop_path")
        val backdropPath: String?, // /eWF3oRyL4QWaidN9F4uvM7cBJUV.jpg
        @SerializedName("first_air_date")
        val firstAirDate: String?, // 2005-10-13
        @SerializedName("genre_ids")
        val genreIds: List<Int>?,
        @SerializedName("id")
        val id: Int?, // 206559
        @SerializedName("name")
        val name: String?, // Binnelanders
        @SerializedName("origin_country")
        val originCountry: List<String>?,
        @SerializedName("original_language")
        val originalLanguage: String?, // af
        @SerializedName("original_name")
        val originalName: String?, // Binnelanders
        @SerializedName("overview")
        val overview: String?,
        @SerializedName("popularity")
        val popularity: Double?, // 2664.418
        @SerializedName("poster_path")
        val posterPath: String?, // /v9nGSRx5lFz6KEgfmgHJMSgaARC.jpg
        @SerializedName("vote_average")
        val voteAverage: Double?, // 5.739
        @SerializedName("vote_count")
        val voteCount: Int?, // 46
    )
}
