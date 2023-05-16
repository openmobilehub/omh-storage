package com.omh.android.storage.api.drive.nongms.data.source

import com.omh.android.storage.api.data.source.remote.FileRemoteDataSource
import com.omh.android.storage.api.domain.model.File
import com.omh.android.storage.api.drive.nongms.data.GoogleRetrofitImpl
import com.omh.android.storage.api.drive.nongms.data.source.mapper.toFileList

internal class FileRemoteDataSource : FileRemoteDataSource {

    override fun getRootFilesList(): List<File> {
        val response = GoogleRetrofitImpl
            .getInstance("")
            .getGoogleStorageApiService()
            .getRootFilesList()
            .execute()

        return if (response.isSuccessful) {
            response.body()?.toFileList().orEmpty()
        } else {
            emptyList()
        }
    }
}
