package com.omh.android.storage.api.drive.nongms.data.source

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

import android.webkit.MimeTypeMap
import com.omh.android.storage.api.data.source.OmhFileRemoteDataSource
import com.omh.android.storage.api.domain.model.OmhFile
import com.omh.android.storage.api.domain.model.OmhStorageException
import com.omh.android.storage.api.domain.model.OmhStorageStatusCodes.DOWNLOAD_ERROR
import com.omh.android.storage.api.domain.model.OmhStorageStatusCodes.DOWNLOAD_GOOGLE_WORKSPACE_ERROR
import com.omh.android.storage.api.domain.model.OmhStorageStatusCodes.UPDATE_CONTENT_FILE
import com.omh.android.storage.api.domain.model.OmhStorageStatusCodes.UPDATE_META_DATA
import com.omh.android.storage.api.drive.nongms.data.GoogleStorageApiService
import com.omh.android.storage.api.drive.nongms.data.retrofit.GoogleRetrofitImpl
import com.omh.android.storage.api.drive.nongms.data.source.body.CreateFileRequestBody
import com.omh.android.storage.api.drive.nongms.data.source.mapper.toFile
import com.omh.android.storage.api.drive.nongms.data.source.mapper.toFileList
import com.omh.android.storage.api.drive.nongms.data.utils.toByteArrayOutputStream
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.HttpException
import java.io.ByteArrayOutputStream
import java.io.File
import java.lang.Exception

internal class NonGmsFileRemoteDataSourceImpl(private val retrofitImpl: GoogleRetrofitImpl) :
    OmhFileRemoteDataSource {

    companion object {
        private const val FILE_NAME_KEY = "name"
        private const val FILE_PARENTS_KEY = "parents"
        private const val ANY_MIME_TYPE = "*/*"
        private const val MEDIA = "media"

        private val JSON_MIME_TYPE = "application/json".toMediaTypeOrNull()

        private const val KEY_ERROR = "error"
        private const val KEY_MESSAGE = "message"
        private const val HTTP_ERROR_MESSAGE = "HTTP Error, code %s\n\n%s"
    }

    @SuppressWarnings("TooGenericExceptionThrown")
    override fun getFilesList(parentId: String): List<OmhFile> {
        val response = retrofitImpl
            .getGoogleStorageApiService()
            .getFilesList(
                query = GoogleStorageApiService.getQueryValue(parentId)
            )
            .execute()

        return if (response.isSuccessful) {
            response.body()?.toFileList().orEmpty()
        } else {
            val jsonError = JSONObject(response.errorBody()?.string().orEmpty())
            val errorMessage = jsonError.getJSONObject(KEY_ERROR).getString(KEY_MESSAGE)
            throw Exception(
                HTTP_ERROR_MESSAGE.format(
                    response.code().toString(),
                    errorMessage
                )
            )
        }
    }

    override fun createFile(name: String, mimeType: String, parentId: String?): OmhFile? {
        val parents = if (parentId.isNullOrBlank()) {
            emptyList()
        } else {
            listOf(parentId)
        }

        val response = retrofitImpl
            .getGoogleStorageApiService()
            .createFile(body = CreateFileRequestBody(mimeType, name, parents))
            .execute()

        return if (response.isSuccessful) {
            response.body()?.toFile()
        } else {
            null
        }
    }

    override fun deleteFile(fileId: String): Boolean {
        val response = retrofitImpl
            .getGoogleStorageApiService()
            .deleteFile(
                fileId = fileId
            )
            .execute()

        return response.isSuccessful
    }

    override fun uploadFile(
        localFileToUpload: File,
        parentId: String?
    ): OmhFile? {
        val stringMimeType = MimeTypeMap
            .getSingleton()
            .getMimeTypeFromExtension(localFileToUpload.extension)
            ?: ANY_MIME_TYPE

        val mimeType = stringMimeType.toMediaTypeOrNull()
        val requestFile = localFileToUpload.asRequestBody(mimeType)
        val parentsList = if (parentId.isNullOrBlank()) {
            emptyList()
        } else {
            listOf(parentId)
        }

        val parentsListAsJson = JSONArray(parentsList)
        val jsonMetaData = JSONObject().apply {
            put(FILE_NAME_KEY, localFileToUpload.name)
            put(FILE_PARENTS_KEY, parentsListAsJson)
        }

        val jsonRequestBody = jsonMetaData.toString().toRequestBody(JSON_MIME_TYPE)
        val filePart = MultipartBody.Part.createFormData(FILE_NAME_KEY, localFileToUpload.name, requestFile)

        val response = retrofitImpl
            .getGoogleStorageApiService()
            .uploadFile(jsonRequestBody, filePart)
            .execute()

        return if (response.isSuccessful) {
            response.body()?.toFile()
        } else {
            null
        }
    }

    override fun downloadFile(fileId: String, mimeType: String?): ByteArrayOutputStream {
        val response = retrofitImpl
            .getGoogleStorageApiService()
            .downloadMediaFile(fileId = fileId, alt = MEDIA)
            .execute()

        val outputStream = response.body().toByteArrayOutputStream()

        return if (response.isSuccessful) {
            outputStream
        } else {
            if (mimeType == null) {
                val cause = HttpException(response)
                throw (OmhStorageException.DownloadException(DOWNLOAD_ERROR, cause))
            }

            return exportDocEditor(fileId, mimeType)
        }
    }

    private fun exportDocEditor(fileId: String, mimeType: String): ByteArrayOutputStream {
        val response = retrofitImpl
            .getGoogleStorageApiService()
            .exportDocEditor(fileId, mimeType)
            .execute()

        val outputStream = response.body().toByteArrayOutputStream()

        return if (response.isSuccessful) {
            outputStream
        } else {
            val cause = HttpException(response)
            throw (OmhStorageException.DownloadException(DOWNLOAD_GOOGLE_WORKSPACE_ERROR, cause))
        }
    }
    override fun updateFile(
        localFileToUpload: File,
        fileId: String
    ): OmhFile? {
        val jsonMetaData = JSONObject().apply {
            put(FILE_NAME_KEY, localFileToUpload.name)
        }

        val jsonRequestBody = jsonMetaData.toString().toRequestBody(JSON_MIME_TYPE)
        val response = retrofitImpl
            .getGoogleStorageApiService()
            .updateMetaData(jsonRequestBody, fileId)
            .execute()

        return if (response.isSuccessful) {
            val omhFile = response.body()?.toFile() ?: return null
            updateMediaFile(localFileToUpload, omhFile)
        } else {
            throw OmhStorageException.UpdateException(UPDATE_META_DATA, HttpException(response))
        }
    }

    private fun updateMediaFile(
        localFileToUpload: File,
        omhFile: OmhFile
    ): OmhFile? {
        val mimeType = omhFile.mimeType.toMediaTypeOrNull()
        val requestFile = localFileToUpload.asRequestBody(mimeType)

        val response = retrofitImpl
            .getGoogleStorageApiService()
            .updateFile(requestFile, omhFile.id)
            .execute()

        return if (response.isSuccessful) {
            response.body()?.toFile()
        } else {
            throw OmhStorageException.UpdateException(UPDATE_CONTENT_FILE, HttpException(response))
        }
    }
}
