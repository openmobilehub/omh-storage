package com.omh.android.storage.api

import android.content.Context
import com.omh.android.auth.api.OmhAuthClient

interface OmhStorageFactory {

    fun getStorageClient(context: Context, authClient: OmhAuthClient): OmhStorageClient
}
