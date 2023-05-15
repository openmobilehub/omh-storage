package com.omh.android.storage.api.data.repository

import com.omh.android.storage.api.data.datasource.network.FilesFoldersNetworkDataSource
import com.omh.android.storage.api.data.mapper.fromFilesFoldersRemoteListToDomain
import com.omh.android.storage.api.domain.abstraction.FilesFoldersRepository

class FilesFoldersDataRepository(
    private val networkDataSource: FilesFoldersNetworkDataSource
) : FilesFoldersRepository {
    override fun getAll() = fromFilesFoldersRemoteListToDomain(
        networkDataSource.getAll()
    )
}
