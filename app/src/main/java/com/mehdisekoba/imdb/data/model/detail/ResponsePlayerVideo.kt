package com.mehdisekoba.imdb.data.model.detail

import com.google.gson.annotations.SerializedName

@Suppress("ktlint:standard:value-parameter-comment")
data class ResponsePlayerVideo(
    @SerializedName("id")
    val id: Int?, // 693134
    @SerializedName("results")
    val results: List<Result?>?,
) {
    data class Result(
        @SerializedName("id")
        val id: String?, // 65ebc0ca28723c01643e7dd4
        @SerializedName("iso_3166_1")
        val iso31661: String?, // US
        @SerializedName("iso_639_1")
        val iso6391: String?, // en
        @SerializedName("key")
        val key: String?, // Xq6OPXGEzBo
        @SerializedName("name")
        val name: String?, // This or That
        @SerializedName("official")
        val official: Boolean?, // true
        @SerializedName("published_at")
        val publishedAt: String?, // 2024-03-08T07:57:42.000Z
        @SerializedName("site")
        val site: String?, // YouTube
        @SerializedName("size")
        val size: Int?, // 1080
        @SerializedName("type")
        val type: String?, // Featurette
    )
}
