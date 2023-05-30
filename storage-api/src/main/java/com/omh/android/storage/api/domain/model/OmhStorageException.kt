package com.omh.android.storage.api.domain.model

import com.omh.android.auth.api.models.OmhAuthStatusCodes

sealed class OmhStorageException(val statusCode: Int) : Exception() {

    override val message: String?
        get() = OmhAuthStatusCodes.getStatusCodeString(statusCode)

    class InvalidCredentialsException(statusCode: Int) : OmhStorageException(statusCode)
}