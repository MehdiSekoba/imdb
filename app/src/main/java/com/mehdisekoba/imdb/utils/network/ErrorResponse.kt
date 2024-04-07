@file:Suppress("ktlint:standard:filename")

package com.mehdisekoba.imdb.utils.network

import com.mehdisekoba.imdb.utils.states.BaseState
import retrofit2.Response

open class ErrorResponse<T>(private val response: Response<T>) {
    fun generalNetworkResponse(): BaseState {
        return when {
            response.message().contains("timeout") -> BaseState.Error("Timeout")
            response.code() == 503 -> BaseState.Error("Service offline: This service is temporarily offline, try again later.")
            response.code() == 403 -> BaseState.Error("The user has been suspended.")
            response.code() == 404 -> BaseState.Error("The resource you requested could not be found.")
            response.code() == 405 -> BaseState.Error("This request method is not supported for this resource.")
            response.code() == 406 -> BaseState.Error("Invalid date range: Should be a range no longer than 14 days.")
            response.code() == 422 -> BaseState.Error("Nothing to update.")
            response.code() == 429 -> BaseState.Error("Your request count (#) is over the allowed limit of (40)")
            response.code() == 500 -> BaseState.Error("Failed.")
            response.code() == 502 -> BaseState.Error("Bad Gateway: Couldn't connect to the backend server.")
            response.code() == 403 -> BaseState.Error("Service offline: This service is temporarily offline, try again later.")
            else -> BaseState.Error(response.message())
        }
    }
}
