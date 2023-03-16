package com.github.openmobilehub.storage.data.repository

import com.github.openmobilehub.storage.data.datasource.network.FolderNetworkDataSource
import com.github.openmobilehub.storage.domain.abstraction.FolderRepository

class FolderDataRepository(
    private val networkDataSource: FolderNetworkDataSource
) : FolderRepository {
    override fun create() {
        networkDataSource.create()
    }

    override fun read() {
        networkDataSource.read()
    }

    override fun update() {
        networkDataSource.update()
    }

    override fun delete() {
        networkDataSource.delete()
    }

    override fun upload() {
        networkDataSource.upload()
    }

    override fun download() {
        networkDataSource.download()
    }
}
