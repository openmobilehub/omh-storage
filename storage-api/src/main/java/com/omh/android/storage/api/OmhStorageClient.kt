package com.omh.android.storage.api

import com.omh.android.auth.api.OmhAuthClient
import com.omh.android.storage.api.domain.repository.FileRepository

abstract class OmhStorageClient protected constructor(
    protected val authClient: OmhAuthClient
) {

    interface Builder {

        fun build(authClient: OmhAuthClient): OmhStorageClient
    }

    abstract fun getRepository(): FileRepository
}
