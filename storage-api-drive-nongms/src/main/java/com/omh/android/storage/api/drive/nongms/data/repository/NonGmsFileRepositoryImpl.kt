package com.omh.android.storage.api.drive.nongms.data.repository

import com.omh.android.storage.api.data.source.FileRemoteDataSource
import com.omh.android.storage.api.domain.repository.FileRepository

class NonGmsFileRepositoryImpl(
    private val networkDataSource: FileRemoteDataSource
) : FileRepository {

    override fun getRootFilesList() = networkDataSource.getRootFilesList()

    override fun create() = Unit

    override fun open() = Unit

    override fun update() = Unit

    override fun delete() = Unit

    override fun upload() = Unit

    override fun download() = Unit
}
