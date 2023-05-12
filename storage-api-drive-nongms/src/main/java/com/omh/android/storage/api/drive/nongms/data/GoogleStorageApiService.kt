package com.omh.android.storage.api.drive.nongms.data

import com.omh.android.storage.api.drive.nongms.data.source.response.FileListRemoteResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

internal object ApiUrl {
    internal const val GET_ALL_FILES_AND_FOLDERS_LIST_PARTICLE = "files"

    internal const val QUERY_Q = "q"
    internal const val QUERY_FIELDS = "fields"

    internal const val Q_VALUE = "'root' in parents and trashed = false"
    internal const val FIELDS_VALUE = "files(id, name, mimeType, modifiedTime)"
}

internal interface GoogleStorageApiService {

    @GET(ApiUrl.GET_ALL_FILES_AND_FOLDERS_LIST_PARTICLE)
    fun getRootFilesList(
        @Query(ApiUrl.QUERY_Q) query: String = ApiUrl.Q_VALUE,
        @Query(ApiUrl.QUERY_FIELDS) fields: String = ApiUrl.FIELDS_VALUE
    ): Call<FileListRemoteResponse>
}
