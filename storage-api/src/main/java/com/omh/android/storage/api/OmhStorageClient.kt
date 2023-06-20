package com.omh.android.storage.api

import com.omh.android.auth.api.OmhAuthClient
import com.omh.android.auth.api.async.OmhTask
import com.omh.android.storage.api.async.OmhStorageTaskImpl
import com.omh.android.storage.api.domain.repository.OmhFileRepository
import com.omh.android.storage.api.domain.usecase.CreateFileUseCase
import com.omh.android.storage.api.domain.usecase.CreateFileUseCaseParams
import com.omh.android.storage.api.domain.usecase.CreateFileUseCaseResult
import com.omh.android.storage.api.domain.usecase.DeleteFileUseCase
import com.omh.android.storage.api.domain.usecase.DeleteFileUseCaseParams
import com.omh.android.storage.api.domain.usecase.DeleteFileUseCaseResult
import com.omh.android.storage.api.domain.usecase.GetFilesListUseCase
import com.omh.android.storage.api.domain.usecase.GetFilesListUseCaseParams
import com.omh.android.storage.api.domain.usecase.GetFilesListUseCaseResult
import com.omh.android.storage.api.domain.usecase.OmhResult
import com.omh.android.storage.api.domain.usecase.UploadFileUseCase

abstract class OmhStorageClient protected constructor(
    protected val authClient: OmhAuthClient
) {

    interface Builder {

        fun build(authClient: OmhAuthClient): OmhStorageClient
    }

    protected abstract fun getRepository(): OmhFileRepository

    fun listFiles(parentId: String): OmhTask<GetFilesListUseCaseResult> {
        val getFilesListUseCase = GetFilesListUseCase(getRepository())
        return OmhStorageTaskImpl {
            val parameters = GetFilesListUseCaseParams(parentId)
            val result: OmhResult<GetFilesListUseCaseResult> = getFilesListUseCase(parameters)
            result
        }
    }

    fun createFile(
        name: String,
        mimeType: String,
        parentId: String
    ): OmhTask<CreateFileUseCaseResult> {
        val createFileUseCase = CreateFileUseCase(getRepository())
        return OmhStorageTaskImpl {
            val parameters = CreateFileUseCaseParams(name, mimeType, parentId)
            val result: OmhResult<CreateFileUseCaseResult> = createFileUseCase(parameters)
            result
        }
    }

    fun deleteFile(id: String): OmhTask<DeleteFileUseCaseResult> {
        val deleteFileUseCase = DeleteFileUseCase(getRepository())
        return OmhStorageTaskImpl {
            val parameters = DeleteFileUseCaseParams(id)
            val result = deleteFileUseCase(parameters)
            result
        }
    }

    fun deleteFile() = DeleteFileUseCase(getRepository())

    fun uploadFile() = UploadFileUseCase(getRepository())
}
