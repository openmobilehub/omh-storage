package com.omh.android.storage.api.drive.gms

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.omh.android.auth.api.OmhAuthClient
import com.omh.android.auth.api.models.OmhAuthStatusCodes
import com.omh.android.storage.api.OmhStorageClient
import com.omh.android.storage.api.domain.model.OmhStorageException
import com.omh.android.storage.api.domain.repository.OmhFileRepository
import com.omh.android.storage.api.drive.gms.data.GoogleDriveApiProvider
import com.omh.android.storage.api.drive.gms.data.GoogleDriveApiService
import com.omh.android.storage.api.drive.gms.data.repository.GmsFileRepositoryImpl
import com.omh.android.storage.api.drive.gms.data.source.GmsFileRemoteDataSourceImpl

internal class OmhGmsStorageClientImpl private constructor(
    authClient: OmhAuthClient
) : OmhStorageClient(authClient) {

    internal class Builder : OmhStorageClient.Builder {

        override fun build(authClient: OmhAuthClient): OmhStorageClient =
            OmhGmsStorageClientImpl(authClient)
    }

    @Throws(OmhStorageException::class)
    override fun getRepository(): OmhFileRepository {
        val credentials = authClient.getCredentials() as? GoogleAccountCredential
            ?: throw OmhStorageException.InvalidCredentialsException(OmhAuthStatusCodes.SIGN_IN_FAILED)

        val apiProvider = GoogleDriveApiProvider.getInstance(credentials)

        val apiService = GoogleDriveApiService(apiProvider)

        val dataSource = GmsFileRemoteDataSourceImpl(apiService)

        return GmsFileRepositoryImpl(dataSource)
    }
}
