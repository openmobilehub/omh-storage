package com.omh.android.storage.api.drive.gms

import com.omh.android.auth.api.OmhAuthClient
import com.omh.android.storage.api.OmhStorageClient
import com.omh.android.storage.api.OmhStorageFactory

internal class OmhGmsStorageFactoryImpl : OmhStorageFactory {

    override fun getStorageClient(
        authClient: OmhAuthClient
    ): OmhStorageClient = OmhGmsStorageClientImpl.Builder().build(authClient)
}
