package com.omh.android.storage.api.drive.nongms.data.source

import com.omh.android.storage.api.data.source.OmhFileRemoteDataSource
import com.omh.android.storage.api.domain.model.OmhFile
import com.omh.android.storage.api.drive.nongms.data.GoogleRetrofitImpl
import com.omh.android.storage.api.drive.nongms.data.source.mapper.toFileList

internal class NonGmsFileRemoteDataSourceImpl(private val retrofitImpl: GoogleRetrofitImpl) : OmhFileRemoteDataSource {

    override fun getRootFilesList(): List<OmhFile> {
        val response = retrofitImpl
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
