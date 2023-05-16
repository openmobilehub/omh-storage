package com.omh.android.storage.api.drive.nongms

import android.content.Context
import com.omh.android.auth.api.OmhAuthClient
import com.omh.android.storage.api.OmhStorageClient

internal class OmhStorageClientImpl(
    context: Context,
    authClient: OmhAuthClient
) : OmhStorageClient(authClient) {

    private val applicationContext: Context

    init {
        applicationContext = context.applicationContext
    }

    internal class Builder : OmhStorageClient.Builder {

        override fun build(context: Context, authClient: OmhAuthClient) =
            OmhStorageClientImpl(context, authClient)
    }
}
