package com.omh.android.storage.api.drive.nongms

import androidx.annotation.Keep
import com.omh.android.auth.api.OmhAuthClient
import com.omh.android.storage.api.OmhStorageClient
import com.omh.android.storage.api.OmhStorageFactory

@Keep
internal class OmhNonGmsStorageFactoryImpl : OmhStorageFactory {

    override fun getStorageClient(
        authClient: OmhAuthClient
    ): OmhStorageClient = OmhNonGmsStorageClientImpl.Builder().build(authClient)
}
