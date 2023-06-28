package com.omh.android.storage.api.drive.nongms.data.source

import android.webkit.MimeTypeMap
import com.omh.android.storage.api.data.source.OmhFileRemoteDataSource
import com.omh.android.storage.api.domain.model.OmhFile
import com.omh.android.storage.api.drive.nongms.data.GoogleRetrofitImpl
import com.omh.android.storage.api.drive.nongms.data.GoogleStorageApiService
import com.omh.android.storage.api.drive.nongms.data.source.body.CreateFileRequestBody
import com.omh.android.storage.api.drive.nongms.data.source.mapper.toFile
import com.omh.android.storage.api.drive.nongms.data.source.mapper.toFileList
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.File

internal class NonGmsFileRemoteDataSourceImpl(private val retrofitImpl: GoogleRetrofitImpl) :
    OmhFileRemoteDataSource {

    companion object {
        private const val FILE_NAME_KEY = "name"
        private const val FILE_PARENTS_KEY = "parents"
        private const val ANY_MIME_TYPE = "*/*"

        private val JSON_MIME_TYPE = "application/json".toMediaTypeOrNull()
    }

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
            emptyList()
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

    override fun downloadFile(fileId: String, mimeType: String?): ByteArrayOutputStream = ByteArrayOutputStream()
}
