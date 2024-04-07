package com.mehdisekoba.imdb.data.model


import com.google.gson.annotations.SerializedName

data class ResponseErrors(
    @SerializedName("status_code")
    val statusCode: Int?, // 7
    @SerializedName("status_message")
    val statusMessage: String?, // Invalid API key: You must be granted a valid key.
    @SerializedName("success")
    val success: Boolean? // false
)