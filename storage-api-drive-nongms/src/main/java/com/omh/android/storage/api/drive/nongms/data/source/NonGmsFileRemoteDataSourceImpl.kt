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
import java.io.File

internal class NonGmsFileRemoteDataSourceImpl(private val retrofitImpl: GoogleRetrofitImpl) :
    OmhFileRemoteDataSource {

    companion object {
        const val FILE_NAME_KEY = "name"
        const val FILE_PARENTS_KEY = "parents"
        const val JSON_MIME_TYPE = "application/json"
        const val ANY_MIME_TYPE = "*/*"
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

    override fun uploadFile(filePath: File, fileName: String, parentId: String?): OmhFile? {
        val mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(filePath.extension) ?: ANY_MIME_TYPE
        val requestFile = filePath.asRequestBody(mimeType.toMediaTypeOrNull())
        val parents = if (parentId.isNullOrBlank()) {
            emptyList()
        } else {
            listOf(parentId)
        }
        val parentsArray = JSONArray(parents)
        val metaData = JSONObject().apply {
            put(FILE_NAME_KEY, fileName)
            put(FILE_PARENTS_KEY, parentsArray)
        }
        val jsonBody = metaData.toString().toRequestBody(JSON_MIME_TYPE.toMediaTypeOrNull())
        val filePart = MultipartBody.Part.createFormData(FILE_NAME_KEY, fileName, requestFile)

        val response = retrofitImpl
            .getGoogleStorageApiService()
            .uploadFile(jsonBody, filePart)
            .execute()

        return if (response.isSuccessful) {
            response.body()?.toFile()
        } else {
            null
        }
    }
}
