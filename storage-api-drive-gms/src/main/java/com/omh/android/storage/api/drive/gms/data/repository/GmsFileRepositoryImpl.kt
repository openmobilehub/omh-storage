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

package com.omh.android.storage.api.drive.gms.data.repository

import com.omh.android.storage.api.data.source.OmhFileRemoteDataSource
import com.omh.android.storage.api.domain.repository.OmhFileRepository
import java.io.File

internal class GmsFileRepositoryImpl(
    private val dataSource: OmhFileRemoteDataSource
) : OmhFileRepository {

    override fun getFilesList(parentId: String) = dataSource.getFilesList(parentId)

    override fun createFile(name: String, mimeType: String, parentId: String?) =
        dataSource.createFile(name, mimeType, parentId)

    override fun deleteFile(fileId: String) = dataSource.deleteFile(fileId)

    override fun uploadFile(localFileToUpload: File, parentId: String?) =
        dataSource.uploadFile(localFileToUpload, parentId)

    override fun downloadFile(fileId: String, mimeType: String?) =
        dataSource.downloadFile(fileId, mimeType)

    override fun updateFile(localFileToUpload: File, fileId: String) =
        dataSource.updateFile(localFileToUpload, fileId)
}
