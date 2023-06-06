package com.omh.android.storage.api

import com.omh.android.auth.api.OmhAuthClient
import com.omh.android.storage.api.domain.repository.FileRepository
import com.omh.android.storage.api.domain.usecase.CreateFileUseCase
import com.omh.android.storage.api.domain.usecase.GetFilesListWithParentIdUseCase

abstract class OmhStorageClient protected constructor(
    protected val authClient: OmhAuthClient
) {

    interface Builder {

        fun build(authClient: OmhAuthClient): OmhStorageClient
    }

    protected abstract fun getRepository(): FileRepository

    /*
     * TODO: This must return an asynchronous task that can be executed with any library
     *  capable to manage this. In the future, this task must be implemented from auth
     *  and will not return an use case
     */
    @SuppressWarnings("ForbiddenComment")
    fun listFiles() = GetFilesListWithParentIdUseCase(getRepository())

    fun createFile() = CreateFileUseCase(getRepository())
}
