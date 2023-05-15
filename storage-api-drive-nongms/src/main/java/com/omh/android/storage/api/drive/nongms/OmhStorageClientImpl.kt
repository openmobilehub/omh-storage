package com.omh.android.storage.api.drive.nongms

import android.content.Context
import com.omh.android.storage.api.OmhStorageClient

internal class OmhStorageClientImpl(
    context: Context
) : OmhStorageClient {

    private val applicationContext: Context

    init {
        applicationContext = context.applicationContext
    }

    internal class Builder : OmhStorageClient.Builder {
        override fun build(context: Context): OmhStorageClient {
            return OmhStorageClientImpl(context)
        }
    }

    override fun setupAccessToken(token: String) {
        // Implement THIS
    }
}
