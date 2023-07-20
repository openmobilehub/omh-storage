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

package com.omh.android.storage.api.domain.repository

import com.omh.android.storage.api.domain.model.OmhFile
import java.io.ByteArrayOutputStream
import java.io.File

interface OmhFileRepository {

    fun getFilesList(parentId: String = "root"): List<OmhFile>

    fun createFile(name: String, mimeType: String, parentId: String?): OmhFile?

    fun deleteFile(fileId: String): Boolean

    fun uploadFile(localFileToUpload: File, parentId: String?): OmhFile?

    fun downloadFile(fileId: String, mimeType: String?): ByteArrayOutputStream

    fun updateFile(localFileToUpload: File, fileId: String): OmhFile?
}
