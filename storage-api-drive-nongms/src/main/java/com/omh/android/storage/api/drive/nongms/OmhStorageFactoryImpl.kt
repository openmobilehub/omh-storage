package com.omh.android.storage.api.drive.nongms

import android.content.Context
import com.omh.android.auth.api.OmhAuthClient
import com.omh.android.storage.api.OmhStorageClient
import com.omh.android.storage.api.OmhStorageFactory

class OmhStorageFactoryImpl : OmhStorageFactory {

    override fun getStorageClient(
        context: Context,
        authClient: OmhAuthClient
    ): OmhStorageClient = OmhStorageClientImpl.Builder().build(context, authClient)
}
