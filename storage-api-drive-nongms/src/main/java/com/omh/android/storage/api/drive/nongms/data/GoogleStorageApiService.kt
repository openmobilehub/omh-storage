package com.omh.android.storage.api.drive.nongms.data

import com.omh.android.storage.api.drive.nongms.data.source.body.CreateFileRequestBody
import com.omh.android.storage.api.drive.nongms.data.source.response.FileListRemoteResponse
import com.omh.android.storage.api.drive.nongms.data.source.response.FileRemoteResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

internal interface GoogleStorageApiService {

    companion object {
        private const val FILES_PARTICLE = "files"

        private const val QUERY_Q = "q"
        private const val QUERY_FIELDS = "fields"

        private const val Q_VALUE = "'%s' in parents and trashed = false"

        private fun getQueryValue(parentId: String) = String.format(Q_VALUE, parentId)

        private const val QUERY_REQUESTED_FIELDS = "id,name,mimeType,modifiedTime,parents"
        private const val FIELDS_VALUE = "files($QUERY_REQUESTED_FIELDS)"
    }

    @GET(FILES_PARTICLE)
    fun getFilesListWithParentId(
        parentId: String = "root",
        @Query(QUERY_Q) query: String = getQueryValue(parentId),
        @Query(QUERY_FIELDS) fields: String = FIELDS_VALUE
    ): Call<FileListRemoteResponse>

    @POST(FILES_PARTICLE)
    fun createFile(
        @Query(QUERY_FIELDS) query: String = QUERY_REQUESTED_FIELDS,
        @Body body: CreateFileRequestBody
    ): Call<FileRemoteResponse>
}
