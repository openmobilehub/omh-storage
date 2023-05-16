package com.omh.android.storage.api

import android.content.Context
import com.omh.android.auth.api.OmhAuthClient

abstract class OmhStorageClient protected constructor(protected val authClient: OmhAuthClient) {

    interface Builder {

        fun build(context: Context, authClient: OmhAuthClient): OmhStorageClient
    }

    abstract fun getRepository()
}
