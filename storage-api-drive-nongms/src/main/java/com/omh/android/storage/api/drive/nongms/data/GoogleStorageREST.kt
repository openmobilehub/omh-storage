package com.omh.android.storage.api.drive.nongms.data

import com.omh.android.storage.api.drive.nongms.data.model.FilesFoldersListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

internal interface GoogleStorageREST {

    @GET("files")
    fun getFilesFoldersList(
        @Query("q") query: String = "'root' in parents and trashed = false",
        @Query("fields") fields: String = "files(id, name, mimeType, modifiedTime)"
    ): Call<FilesFoldersListResponse>
}
