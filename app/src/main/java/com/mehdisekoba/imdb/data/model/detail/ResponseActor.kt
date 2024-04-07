package com.mehdisekoba.imdb.data.model.detail

import com.google.gson.annotations.SerializedName

data class ResponseActor(
    @SerializedName("cast")
    val cast: List<Cast>?,
    @SerializedName("crew")
    val crew: List<Crew?>?,
    @SerializedName("id")
    val id: Int?, // 1622
) {
    data class Cast(
        @SerializedName("adult")
        val adult: Boolean?, // false
        @SerializedName("backdrop_path")
        val backdropPath: String?, // /3Wr9M9Pee1kLgfbRSrG0LHy0GsP.jpg
        @SerializedName("character")
        val character: String?, // Jang-jin
        @SerializedName("credit_id")
        val creditId: String?, // 52fe44d4c3a368484e03936d
        @SerializedName("genre_ids")
        val genreIds: List<Int?>?,
        @SerializedName("id")
        val id: Int?, // 25647
        @SerializedName("order")
        val order: Int?, // 0
        @SerializedName("original_language")
        val originalLanguage: String?, // ko
        @SerializedName("original_title")
        val originalTitle: String?, // 숨
        @SerializedName("overview")
        val overview: String?, // A condemned prisoner slowly falls in love with the married female artist who decorates his prison cell. Jin is a convicted killer awaiting execution on Death Row; Yeon is a lonely artist locked in a loveless marriage.
        @SerializedName("popularity")
        val popularity: Double?, // 8.545
        @SerializedName("poster_path")
        val posterPath: String?, // /uDkDzSUluhC3ONk25VbaVaqe8kH.jpg
        @SerializedName("release_date")
        val releaseDate: String?, // 2007-04-26
        @SerializedName("title")
        val title: String?, // Breath
        @SerializedName("video")
        val video: Boolean?, // false
        @SerializedName("vote_average")
        val voteAverage: Double?, // 6.7
        @SerializedName("vote_count")
        val voteCount: Int?, // 91
    )

    data class Crew(
        @SerializedName("adult")
        val adult: Boolean?, // false
        @SerializedName("backdrop_path")
        val backdropPath: String?, // /yPGRx9VrA98UlS5QfXrNws34D82.jpg
        @SerializedName("credit_id")
        val creditId: String?, // 593a850d9251411eed00bfbc
        @SerializedName("department")
        val department: String?, // Directing
        @SerializedName("genre_ids")
        val genreIds: List<Int?>?,
        @SerializedName("id")
        val id: Int?, // 454817
        @SerializedName("job")
        val job: String?, // Director
        @SerializedName("original_language")
        val originalLanguage: String?, // zh
        @SerializedName("original_title")
        val originalTitle: String?, // 三生
        @SerializedName("overview")
        val overview: String?,
        @SerializedName("popularity")
        val popularity: Double?, // 1.422
        @SerializedName("poster_path")
        val posterPath: String?, // /8rXmyBf99B2xeaaOqxBHK7riell.jpg
        @SerializedName("release_date")
        val releaseDate: String?, // 2014-08-15
        @SerializedName("title")
        val title: String?, // Three Charmed Lives
        @SerializedName("video")
        val video: Boolean?, // false
        @SerializedName("vote_average")
        val voteAverage: Double?, // 5
        @SerializedName("vote_count")
        val voteCount: Int?, // 1
    )
}
