package com.omh.android.storage.api.drive.nongms.data.source

import com.omh.android.auth.api.OmhCredentials
import com.omh.android.storage.api.data.source.remote.FileRemoteDataSource
import com.omh.android.storage.api.domain.model.File
import com.omh.android.storage.api.drive.nongms.data.GoogleRetrofitImpl
import com.omh.android.storage.api.drive.nongms.data.source.mapper.toFileList

internal class NonGmsFileRemoteDataSourceImpl(private val authCredentials: OmhCredentials) : FileRemoteDataSource {

    override fun getRootFilesList(): List<File> {
        val response = GoogleRetrofitImpl
            .getInstance(authCredentials)
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
