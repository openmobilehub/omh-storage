package com.omh.android.storage.api.drive.nongms

import com.omh.android.auth.api.OmhAuthClient
import com.omh.android.storage.api.OmhStorageClient
import com.omh.android.storage.api.OmhStorageFactory

class OmhStorageFactoryImpl : OmhStorageFactory {

    override fun getStorageClient(
        authClient: OmhAuthClient
    ): OmhStorageClient = OmhStorageClientImpl.Builder().build(authClient)
}
