package com.omh.android.storage.api.drive.gms.data.source

import com.google.api.services.drive.model.File
import com.google.api.services.drive.model.FileList
import com.omh.android.storage.api.data.source.OmhFileRemoteDataSource
import com.omh.android.storage.api.domain.model.OmhFile
import com.omh.android.storage.api.drive.gms.data.GoogleDriveApiService
import com.omh.android.storage.api.drive.gms.data.source.mapper.toOmhFile

internal class GmsFileRemoteDataSourceImpl(private val apiService: GoogleDriveApiService) :
    OmhFileRemoteDataSource {

    override fun getFilesList(parentId: String): List<OmhFile> {
        val googleJsonFileList: FileList = apiService.getFilesList().execute()
        val googleFileList: List<File> = googleJsonFileList.files.toList()
        return googleFileList.mapNotNull { googleFile -> googleFile.toOmhFile() }
    }

    override fun createFile(name: String, mimeType: String, parentId: String?): OmhFile? {
        val fileToBeCreated = File().apply {
            this.name = name
            this.mimeType = mimeType
            if (parentId != null) {
                this.parents = listOf(parentId)
            }
        }

        val responseFile: File = apiService.createFile(fileToBeCreated).execute()

        return responseFile.toOmhFile()
    }

    @SuppressWarnings("TooGenericExceptionCaught", "SwallowedException")
    override fun deleteFile(fileId: String): Boolean {
        return try {
            apiService.deleteFile(fileId).execute()
            true
        } catch (e: Exception) {
            false
        }
    }
}
