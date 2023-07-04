package com.omh.android.storage.api.drive.nongms.data

import com.omh.android.storage.api.drive.nongms.data.source.body.CreateFileRequestBody
import com.omh.android.storage.api.drive.nongms.data.source.response.FileListRemoteResponse
import com.omh.android.storage.api.drive.nongms.data.source.response.FileRemoteResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

internal interface GoogleStorageApiService {

    companion object {
        private const val FILES_PARTICLE = "drive/v3/files"
        private const val UPLOAD_FILES_PARTICLE = "upload/drive/v3/files"

        private const val QUERY_Q = "q"
        private const val QUERY_FIELDS = "fields"
        private const val QUERY_MIME_TYPE = "mimeType"
        private const val QUERY_ALT = "alt"

        private const val Q_VALUE = "'%s' in parents and trashed = false"

        internal fun getQueryValue(parentId: String = "root") = String.format(Q_VALUE, parentId)

        private const val QUERY_REQUESTED_FIELDS = "id,name,mimeType,modifiedTime,parents"
        private const val FIELDS_VALUE = "files($QUERY_REQUESTED_FIELDS)"

        private const val FILE_ID = "fileId"
        private const val META_DATA = "metadata"
    }

    @GET(FILES_PARTICLE)
    fun getFilesList(
        @Query(QUERY_Q) query: String,
        @Query(QUERY_FIELDS) fields: String = FIELDS_VALUE
    ): Call<FileListRemoteResponse>

    @POST(FILES_PARTICLE)
    fun createFile(
        @Query(QUERY_FIELDS) query: String = QUERY_REQUESTED_FIELDS,
        @Body body: CreateFileRequestBody
    ): Call<FileRemoteResponse>

    @DELETE("$FILES_PARTICLE/{$FILE_ID}")
    fun deleteFile(
        @Path(FILE_ID) fileId: String
    ): Call<ResponseBody>

    @Multipart
    @POST(UPLOAD_FILES_PARTICLE)
    fun uploadFile(
        @Part(META_DATA) metadata: RequestBody,
        @Part filePart: MultipartBody.Part
    ): Call<FileRemoteResponse>

    @GET("$FILES_PARTICLE/{$FILE_ID}")
    fun downloadMediaFile(
        @Path(FILE_ID) fileId: String,
        @Query(QUERY_ALT) alt: String
    ): Call<ResponseBody>

    @GET("$FILES_PARTICLE/{$FILE_ID}/export")
    fun exportDocEditor(
        @Path(FILE_ID) fileId: String,
        @Query(QUERY_MIME_TYPE) mimeType: String
    ): Call<ResponseBody>

    @PATCH("$UPLOAD_FILES_PARTICLE/{$FILE_ID}")
    fun updateFile(
        @Body filePart: RequestBody,
        @Path(FILE_ID) fileId: String
    ): Call<FileRemoteResponse>

    @PATCH("$FILES_PARTICLE/{$FILE_ID}")
    fun updateMetaData(
        @Body filePart: RequestBody,
        @Path(FILE_ID) fileId: String
    ): Call<FileRemoteResponse>
}
