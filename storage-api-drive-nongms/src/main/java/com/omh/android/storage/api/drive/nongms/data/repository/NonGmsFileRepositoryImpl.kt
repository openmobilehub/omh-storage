package com.omh.android.storage.api.drive.nongms.data.repository

import com.omh.android.storage.api.data.source.OmhFileRemoteDataSource
import com.omh.android.storage.api.domain.repository.FileRepository

internal class NonGmsFileRepositoryImpl(
    private val dataSource: OmhFileRemoteDataSource
) : FileRepository {

    override fun getFilesListWithParentId(parentId: String) =
        dataSource.getFilesListWithParentId(parentId)

    override fun createFile(name: String, mimeType: String, parentId: String?) =
        dataSource.createFile(name, mimeType, parentId)

    override fun delete(fileId: String) = dataSource.deleteFile(fileId)

    override fun open() = Unit

    override fun update() = Unit

    override fun upload() = Unit

    override fun download() = Unit
}
