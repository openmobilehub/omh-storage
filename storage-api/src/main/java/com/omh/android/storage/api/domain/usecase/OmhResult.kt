package com.omh.android.storage.api.domain.usecase

sealed class OmhResult<out R> {

    companion object {
        internal const val TAG_SUCCESS = "[RESULT SUCCESS -> data: %s]"
        internal const val TAG_ERROR = "[RESULT ERROR -> exception: %s]"
    }

    data class OmhSuccess<out T>(val data: T) : OmhResult<T>()

    data class OmhError(val exception: Exception) : OmhResult<Nothing>()

    override fun toString() = when (this) {
        is OmhSuccess<*> -> String.format(TAG_SUCCESS, data.toString())
        is OmhError -> String.format(TAG_ERROR, exception.toString())
    }
}

object NoParams

object NoResult
