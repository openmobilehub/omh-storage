package com.omh.android.storage.api.drive.nongms

import android.content.Context
import com.omh.android.storage.api.OmhStorageFactory

class OmhStorageFactoryImpl : OmhStorageFactory {

    override fun getStorageClient(
        context: Context
    ) = OmhStorageClientImpl.Builder().build(context)
}
