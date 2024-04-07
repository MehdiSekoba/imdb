package com.mehdisekoba.imdb.data.model.detail

import com.google.gson.annotations.SerializedName

@Suppress("ktlint:standard:max-line-length")
data class ResponseDetail(
    @SerializedName("adult")
    val adult: Boolean?, // false
    @SerializedName("backdrop_path")
    val backdropPath: String?, // /87IVlclAfWL6mdicU1DDuxdwXwe.jpg
    @SerializedName("belongs_to_collection")
    val belongsToCollection: BelongsToCollection?,
    @SerializedName("budget")
    val budget: Int?, // 190000000
    @SerializedName("genres")
    val genres: List<Genre>?,
    @SerializedName("homepage")
    val homepage: String?, // https://www.dunemovie.com
    @SerializedName("id")
    val id: Int?, // 693134
    @SerializedName("imdb_id")
    val imdbId: String?, // tt15239678
    @SerializedName("original_language")
    val originalLanguage: String?, // en
    @SerializedName("original_title")
    val originalTitle: String?, // Dune: Part Two
    @SerializedName("overview")
    val overview: String?, // Follow the mythic journey of Paul Atreides as he unites with Chani and the Fremen while on a path of revenge against the conspirators who destroyed his family. Facing a choice between the love of his life and the fate of the known universe, Paul endeavors to prevent a terrible future only he can foresee.
    @SerializedName("popularity")
    val popularity: Double?, // 736.807
    @SerializedName("poster_path")
    val posterPath: String?, // /8b8R8l88Qje9dn9OE8PY05Nxl1X.jpg
    @SerializedName("production_companies")
    val productionCompanies: List<ProductionCompany?>?,
    @SerializedName("production_countries")
    val productionCountries: List<ProductionCountry?>?,
    @SerializedName("release_date")
    val releaseDate: String?, // 2024-02-27
    @SerializedName("revenue")
    val revenue: Int?, // 509672149
    @SerializedName("runtime")
    val runtime: Int?, // 167
    @SerializedName("spoken_languages")
    val spokenLanguages: List<SpokenLanguage?>?,
    @SerializedName("status")
    val status: String?, // Released
    @SerializedName("tagline")
    val tagline: String?, // Long live the fighters.
    @SerializedName("title")
    val title: String?, // Dune: Part Two
    @SerializedName("video")
    val video: Boolean?, // false
    @SerializedName("vote_average")
    val voteAverage: Double?, // 8.396
    @SerializedName("vote_count")
    val voteCount: Int?, // 1993
) {
    data class BelongsToCollection(
        @SerializedName("backdrop_path")
        val backdropPath: String?, // /ygVSGv86R0BTOKJIb8RQ1sFxs4q.jpg
        @SerializedName("id")
        val id: Int?, // 726871
        @SerializedName("name")
        val name: String?, // Dune Collection
        @SerializedName("poster_path")
        val posterPath: String?, // /wcVafar6Efk3YgFvh8oZQ4yHL6H.jpg
    )

    data class Genre(
        @SerializedName("id")
        val id: Int?, // 878
        @SerializedName("name")
        val name: String?, // Science Fiction
    )

    data class ProductionCompany(
        @SerializedName("id")
        val id: Int?, // 923
        @SerializedName("logo_path")
        val logoPath: String?, // /8M99Dkt23MjQMTTWukq4m5XsEuo.png
        @SerializedName("name")
        val name: String?, // Legendary Pictures
        @SerializedName("origin_country")
        val originCountry: String?, // US
    )

    data class ProductionCountry(
        @SerializedName("iso_3166_1")
        val iso31661: String?, // US
        @SerializedName("name")
        val name: String?, // United States of America
    )

    data class SpokenLanguage(
        @SerializedName("english_name")
        val englishName: String?, // English
        @SerializedName("iso_639_1")
        val iso6391: String?, // en
        @SerializedName("name")
        val name: String?, // English
    )
}
