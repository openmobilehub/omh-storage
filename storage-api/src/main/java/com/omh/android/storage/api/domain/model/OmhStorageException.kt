package com.omh.android.storage.api.domain.model

import com.omh.android.auth.api.models.OmhAuthStatusCodes

sealed class OmhStorageException(val statusCode: Int) : Exception() {

    override val message: String?
        get() = OmhAuthStatusCodes.getStatusCodeString(statusCode)

    class InvalidCredentialsException(statusCode: Int) : OmhStorageException(statusCode)

    class ApiException(statusCode: Int) : OmhStorageException(statusCode)

    class DownloadException(
        statusCode: Int,
        override val message: String
    ) : OmhStorageException(statusCode)

    class UpdateException(
        statusCode: Int,
        override val message: String
    ) : OmhStorageException(statusCode)
}
