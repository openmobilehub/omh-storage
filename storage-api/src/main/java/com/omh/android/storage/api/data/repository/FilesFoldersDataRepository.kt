package com.omh.android.storage.api.data.repository

import com.omh.android.storage.api.data.source.remote.FileRemoteDataSource
import com.omh.android.storage.api.domain.repository.FilesFoldersRepository

class FilesFoldersDataRepository(
    private val networkDataSource: FileRemoteDataSource
) : FilesFoldersRepository {

    override fun getRootFilesList() = networkDataSource.getRootFilesList()

    override fun create() = Unit

    override fun open() = Unit

    override fun update() = Unit

    override fun delete() = Unit

    override fun upload() = Unit

    override fun download() = Unit
}
