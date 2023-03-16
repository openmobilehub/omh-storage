package com.github.openmobilehub.storage.data.repository

import com.github.openmobilehub.storage.data.datasource.network.FilesFoldersNetworkDataSource
import com.github.openmobilehub.storage.domain.abstraction.FilesFoldersRepository

class FilesFoldersDataRepository(
    private val networkDataSource: FilesFoldersNetworkDataSource
) : FilesFoldersRepository {
    override fun getAll() {
        networkDataSource.getAll()
    }
}
