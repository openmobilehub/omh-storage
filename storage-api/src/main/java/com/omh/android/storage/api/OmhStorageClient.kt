package com.omh.android.storage.api

import android.content.Context

interface OmhStorageClient {

    interface Builder {
        fun build(context: Context): OmhStorageClient
    }

    fun setupAccessToken(token: String)
}
