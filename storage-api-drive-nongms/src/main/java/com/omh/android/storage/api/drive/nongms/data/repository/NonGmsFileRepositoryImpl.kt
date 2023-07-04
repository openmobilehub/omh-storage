package com.omh.android.storage.api.drive.nongms.data.repository

import com.omh.android.storage.api.data.source.OmhFileRemoteDataSource
import com.omh.android.storage.api.domain.model.OmhFile
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

    override fun uploadFile(localFileToUpload: File, parentId: String?) =
        dataSource.uploadFile(localFileToUpload, parentId)

    override fun downloadFile(fileId: String, mimeType: String?) =
        dataSource.downloadFile(fileId, mimeType)

    override fun updateFile(localFileToUpload: File, fileId: String): OmhFile? =
        dataSource.updateFile(localFileToUpload, fileId)

    override fun open() = Unit
}
