package com.mehdisekoba.imdb.utils.states

sealed class BaseState(val error: String? = null) {
    data object Idle : BaseState()

    data object Loading : BaseState()

    data object Empty : BaseState()

    class Error(error: String?) : BaseState(error)
}
