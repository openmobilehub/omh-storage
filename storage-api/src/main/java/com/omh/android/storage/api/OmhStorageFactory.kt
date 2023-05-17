package com.omh.android.storage.api

import com.omh.android.auth.api.OmhAuthClient

interface OmhStorageFactory {

    fun getStorageClient(authClient: OmhAuthClient): OmhStorageClient
}
