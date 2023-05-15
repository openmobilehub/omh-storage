package com.omh.android.storage.api

import android.content.Context

interface OmhStorageFactory {
    fun getStorageClient(context: Context): OmhStorageClient
}
