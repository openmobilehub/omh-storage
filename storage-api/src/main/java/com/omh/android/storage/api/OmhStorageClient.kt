/*
 * Copyright 2023 Open Mobile Hub
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
import com.omh.android.storage.api.domain.usecase.UpdateFileUseCase
import com.omh.android.storage.api.domain.usecase.UpdateFileUseCaseParams
import com.omh.android.storage.api.domain.usecase.UpdateFileUseCaseResult
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

    /**
     * This method list files from an specific folder
     *
     * @param parentId The id of the folder you want to get the list of files
     *
     * @return An OmhTask with the result of the operation
     */
    fun listFiles(parentId: String): OmhTask<GetFilesListUseCaseResult> {
        val getFilesListUseCase = GetFilesListUseCase(getRepository())
        return OmhStorageTaskImpl {
            val parameters = GetFilesListUseCaseParams(parentId)
            val result: OmhResult<GetFilesListUseCaseResult> = getFilesListUseCase(parameters)
            result
        }
    }

    /**
     * This method create files in an specific folder
     *
     * @param name The name of the file to be created
     * @param mimeType The mimeType of the file to be created
     * @param parentId The id of the folder where the file will be created
     *
     * @return An OmhTask with the result of the operation
     */
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

    /**
     * This method delete files with a given file id
     *
     * @param id The id of the desired file to delete
     *
     * @return An OmhTask with the result of the operation
     */
    fun deleteFile(id: String): OmhTask<DeleteFileUseCaseResult> {
        val deleteFileUseCase = DeleteFileUseCase(getRepository())
        return OmhStorageTaskImpl {
            val parameters = DeleteFileUseCaseParams(id)
            val result: OmhResult<DeleteFileUseCaseResult> = deleteFileUseCase(parameters)
            result
        }
    }

    /**
     * This method upload a file in an specific folder
     *
     * @param localFileToUpload The file to be uploaded
     * @param parentId The id of the folder where the file will be uploaded
     *
     * @return An OmhTask with the result of the operation
     */
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

    /**
     * This method download a file with a given mime type and a given id
     *
     * @param fileId The id fo the file to be downloaded
     * @param mimeType The mimeType of the file to be downloaded
     *
     * @return An OmhTask with the result of the operation
     */
    fun downloadFile(fileId: String, mimeType: String?): OmhTask<DownloadFileUseCaseResult> {
        val downloadFileUseCase = DownloadFileUseCase(getRepository())
        return OmhStorageTaskImpl {
            val parameters = DownloadFileUseCaseParams(fileId, mimeType)
            val result: OmhResult<DownloadFileUseCaseResult> = downloadFileUseCase(parameters)
            result
        }
    }

    /**
     * This method update a remote file with the content of a local file
     *
     * @param localFileToUpload The local file to be uploaded
     * @param fileId The id of the desired file to be updated
     *
     * @return An OmhTask with the result of the operation
     */
    fun updateFile(
        localFileToUpload: File,
        fileId: String
    ): OmhTask<UpdateFileUseCaseResult> {
        val updateFileUseCase = UpdateFileUseCase(getRepository())
        return OmhStorageTaskImpl {
            val parameters = UpdateFileUseCaseParams(localFileToUpload, fileId)
            val result: OmhResult<UpdateFileUseCaseResult> = updateFileUseCase(parameters)
            result
        }
    }
}
