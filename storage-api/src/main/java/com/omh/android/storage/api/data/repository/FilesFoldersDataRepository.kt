package com.omh.android.storage.api.data.repository

import com.omh.android.storage.api.data.source.remote.FileRemoteDataSource
import com.omh.android.storage.api.domain.repository.FilesFoldersRepository

class FilesFoldersDataRepository(
    private val networkDataSource: FileRemoteDataSource
) : FilesFoldersRepository {

    override fun getRootFilesList() = networkDataSource.getRootFilesList()
}
