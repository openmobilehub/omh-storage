package com.omh.android.storage.api.drive.gms.data

import com.google.api.services.drive.Drive
import com.google.api.services.drive.model.File

internal class GoogleDriveApiService(private val apiProvider: GoogleDriveApiProvider) {

    fun getFilesList(parentId: String): Drive.Files.List = apiProvider
        .googleDriveApiService
        .files()
        .list().apply {
            if (parentId.isNotEmpty()) {
                q = "'$parentId' in parents"
            }
        }

    fun createFile(file: File): Drive.Files.Create = apiProvider
        .googleDriveApiService
        .files()
        .create(file)

    fun deleteFile(fileId: String): Drive.Files.Delete = apiProvider
        .googleDriveApiService
        .files()
        .delete(fileId)
}
