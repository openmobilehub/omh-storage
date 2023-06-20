package com.omh.android.storage.api.drive.gms.data.repository

import com.omh.android.storage.api.data.source.OmhFileRemoteDataSource
import com.omh.android.storage.api.domain.model.OmhFile
import com.omh.android.storage.api.domain.repository.OmhFileRepository
import java.io.File

internal class GmsFileRepositoryImpl(private val dataSource: OmhFileRemoteDataSource) : OmhFileRepository {

    override fun getFilesList(parentId: String) = dataSource.getFilesList(parentId)

    override fun createFile(name: String, mimeType: String, parentId: String?) =
        dataSource.createFile(name, mimeType, parentId)

    override fun deleteFile(fileId: String) = dataSource.deleteFile(fileId)
    override fun uploadFile(filePath: File, fileName: String, parentId: String?): OmhFile? =
        dataSource.uploadFile(filePath, fileName, parentId)

    override fun open() = Unit

    override fun update() = Unit

    override fun download() = Unit
}
