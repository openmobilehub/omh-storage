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
import com.omh.android.storage.api.domain.usecase.DownloadFileUseCase
import com.omh.android.storage.api.domain.usecase.DownloadFileUseCaseParams
import com.omh.android.storage.api.domain.usecase.DownloadFileUseCaseResult
import com.omh.android.storage.api.domain.usecase.GetFilesListUseCase
import com.omh.android.storage.api.domain.usecase.GetFilesListUseCaseParams
import com.omh.android.storage.api.domain.usecase.GetFilesListUseCaseResult
import com.omh.android.storage.api.domain.usecase.OmhResult
import com.omh.android.storage.api.domain.usecase.UploadFileUseCase
import com.omh.android.storage.api.domain.usecase.UploadFileUseCaseParams
import com.omh.android.storage.api.domain.usecase.UploadFileUseCaseResult
import java.io.File

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
            val result: OmhResult<DeleteFileUseCaseResult> = deleteFileUseCase(parameters)
            result
        }
    }

    fun uploadFile(
        localFileToUpload: File,
        parentId: String?
    ): OmhTask<UploadFileUseCaseResult> {
        val uploadFileUseCase = UploadFileUseCase(getRepository())
        return OmhStorageTaskImpl {
            val parameters = UploadFileUseCaseParams(localFileToUpload, parentId)
            val result: OmhResult<UploadFileUseCaseResult> = uploadFileUseCase(parameters)
            result
        }
    }

    fun downloadFile(fileId: String, mimeType: String?): OmhTask<DownloadFileUseCaseResult> {
        val downloadFileUseCase = DownloadFileUseCase(getRepository())
        return OmhStorageTaskImpl {
            val parameters = DownloadFileUseCaseParams(fileId, mimeType)
            val result: OmhResult<DownloadFileUseCaseResult> = downloadFileUseCase(parameters)
            result
        }
    }
}
