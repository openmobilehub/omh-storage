package com.omh.android.storage.api.drive.nongms.data.repository

import com.omh.android.storage.api.data.source.OmhFileRemoteDataSource
import com.omh.android.storage.api.domain.repository.FileRepository

internal class NonGmsFileRepositoryImpl(
    private val dataSource: OmhFileRemoteDataSource
) : FileRepository {

    override fun getRootFilesList() = dataSource.getRootFilesList()

    override fun create() = Unit

    override fun open() = Unit

    override fun update() = Unit

    override fun delete() = Unit

    override fun upload() = Unit

    override fun download() = Unit
}
