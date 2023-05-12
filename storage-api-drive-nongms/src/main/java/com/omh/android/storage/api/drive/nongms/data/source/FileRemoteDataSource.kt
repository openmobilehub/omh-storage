package com.omh.android.storage.api.drive.nongms.data.source

import com.omh.android.storage.api.data.source.remote.FileRemoteDataSource
import com.omh.android.storage.api.domain.model.File
import com.omh.android.storage.api.drive.nongms.data.GoogleRetrofitImpl
import com.omh.android.storage.api.drive.nongms.data.source.mapper.toFileResponseList

internal class FileRemoteDataSource : FileRemoteDataSource {

    override fun getRootFilesList(): List<File> {
        val response = GoogleRetrofitImpl
            .getGoogleStorageApiService()
            .getRootFilesList()
            .execute()

        return if (response.isSuccessful) {
            response.body()?.toFileResponseList().orEmpty()
        } else {
            emptyList()
        }
    }
}
