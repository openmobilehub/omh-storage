package com.omh.android.storage.api.drive.nongms.data

import com.omh.android.storage.api.drive.nongms.data.source.response.FileListRemoteResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

internal interface GoogleStorageApiService {

    companion object {
        private const val GET_ALL_FILES_AND_FOLDERS_LIST_PARTICLE = "files"

        private const val QUERY_Q = "q"
        private const val QUERY_FIELDS = "fields"

        private const val Q_VALUE = "'root' in parents and trashed = false"
        private const val FIELDS_VALUE = "files(id, name, mimeType, modifiedTime)"
    }

    @GET(GET_ALL_FILES_AND_FOLDERS_LIST_PARTICLE)
    fun getRootFilesList(
        @Query(QUERY_Q) query: String = Q_VALUE,
        @Query(QUERY_FIELDS) fields: String = FIELDS_VALUE
    ): Call<FileListRemoteResponse>
}
