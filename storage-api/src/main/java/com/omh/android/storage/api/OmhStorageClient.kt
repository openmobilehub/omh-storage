package com.omh.android.storage.api

import com.omh.android.auth.api.OmhAuthClient
import com.omh.android.storage.api.domain.repository.OmhFileRepository
import com.omh.android.storage.api.domain.usecase.CreateFileUseCase
import com.omh.android.storage.api.domain.usecase.DeleteFileUseCase
import com.omh.android.storage.api.domain.usecase.GetFilesListUseCase

abstract class OmhStorageClient protected constructor(
    protected val authClient: OmhAuthClient
) {

    interface Builder {

        fun build(authClient: OmhAuthClient): OmhStorageClient
    }

    protected abstract fun getRepository(): OmhFileRepository

    /*
     * TODO: This must return an asynchronous task that can be executed with any library
     *  capable to manage this. In the future, this task must be implemented from auth
     *  and will not return an use case
     */
    @SuppressWarnings("ForbiddenComment")
    fun listFiles() = GetFilesListUseCase(getRepository())

    fun createFile() = CreateFileUseCase(getRepository())

    fun deleteFile() = DeleteFileUseCase(getRepository())
}
