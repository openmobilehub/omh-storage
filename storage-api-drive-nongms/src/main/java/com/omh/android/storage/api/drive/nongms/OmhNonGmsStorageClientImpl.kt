package com.omh.android.storage.api.drive.nongms

import com.omh.android.auth.api.OmhAuthClient
import com.omh.android.auth.api.OmhCredentials
import com.omh.android.auth.api.models.OmhAuthStatusCodes
import com.omh.android.storage.api.OmhStorageClient
import com.omh.android.storage.api.domain.model.OmhStorageException
import com.omh.android.storage.api.domain.repository.OmhFileRepository
import com.omh.android.storage.api.drive.nongms.data.repository.NonGmsFileRepositoryImpl
import com.omh.android.storage.api.drive.nongms.data.retrofit.GoogleRetrofitImpl
import com.omh.android.storage.api.drive.nongms.data.source.NonGmsFileRemoteDataSourceImpl

internal class OmhNonGmsStorageClientImpl private constructor(
    authClient: OmhAuthClient
) : OmhStorageClient(authClient) {

    internal class Builder : OmhStorageClient.Builder {

        override fun build(authClient: OmhAuthClient): OmhStorageClient =
            OmhNonGmsStorageClientImpl(authClient)
    }

    @Throws(OmhStorageException::class)
    override fun getRepository(): OmhFileRepository {
        val omhCredentials = authClient.getCredentials() as? OmhCredentials
            ?: throw OmhStorageException.InvalidCredentialsException(OmhAuthStatusCodes.SIGN_IN_FAILED)

        val retrofitImpl = GoogleRetrofitImpl.getInstance(omhCredentials)

        val dataSource = NonGmsFileRemoteDataSourceImpl(retrofitImpl)

        return NonGmsFileRepositoryImpl(dataSource)
    }
}
