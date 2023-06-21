package com.omh.android.storage.api.drive.nongms.data.repository

import com.omh.android.storage.api.data.source.OmhFileRemoteDataSource
import com.omh.android.storage.api.domain.repository.OmhFileRepository
import java.io.File

internal class NonGmsFileRepositoryImpl(
    private val dataSource: OmhFileRemoteDataSource
) : OmhFileRepository {

    override fun getFilesList(parentId: String) =
        dataSource.getFilesList(parentId)

    override fun createFile(name: String, mimeType: String, parentId: String?) =
        dataSource.createFile(name, mimeType, parentId)

    override fun deleteFile(fileId: String) = dataSource.deleteFile(fileId)

    override fun uploadFile(localFileToUpload: File, fileName: String, parentId: String?) =
        dataSource.uploadFile(localFileToUpload, fileName, parentId)

    override fun open() = Unit

    override fun update() = Unit

    override fun download() = Unit
}
