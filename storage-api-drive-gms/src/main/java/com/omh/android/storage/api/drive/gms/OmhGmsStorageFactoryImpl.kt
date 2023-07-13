package com.omh.android.storage.api.drive.gms

import androidx.annotation.Keep
import com.omh.android.auth.api.OmhAuthClient
import com.omh.android.storage.api.OmhStorageClient
import com.omh.android.storage.api.OmhStorageFactory

@Keep
internal class OmhGmsStorageFactoryImpl : OmhStorageFactory {

    override fun getStorageClient(
        authClient: OmhAuthClient
    ): OmhStorageClient = OmhGmsStorageClientImpl.Builder().build(authClient)
}
