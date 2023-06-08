package com.omh.android.storage.api.drive.gms

import com.omh.android.auth.api.OmhAuthClient
import com.omh.android.storage.api.OmhStorageClient
import com.omh.android.storage.api.domain.model.OmhStorageException
import com.omh.android.storage.api.domain.repository.OmhFileRepository
import com.omh.android.storage.api.drive.gms.data.repository.GmsFileRepositoryImpl
import com.omh.android.storage.api.drive.gms.data.source.GmsFileRemoteDataSourceImpl
import kotlin.jvm.Throws

internal class OmhGmsStorageClientImpl private constructor(
    authClient: OmhAuthClient
) : OmhStorageClient(authClient) {

    internal class Builder : OmhStorageClient.Builder {

        override fun build(authClient: OmhAuthClient): OmhStorageClient =
            OmhGmsStorageClientImpl(authClient)
    }

    @Throws(OmhStorageException::class)
    override fun getRepository(): OmhFileRepository {
        val dataSource = GmsFileRemoteDataSourceImpl()

        return GmsFileRepositoryImpl(dataSource)
    }
}
