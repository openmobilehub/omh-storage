package com.omh.android.storage.api.drive.gms.data

import com.google.api.services.drive.Drive
import com.google.api.services.drive.model.File

internal class GoogleDriveApiService(private val apiProvider: GoogleDriveApiProvider) {

    fun getFilesList(): Drive.Files.List = apiProvider
        .googleDriveApiService
        .files()
        .list()

    fun createFile(file: File): Drive.Files.Create = apiProvider
        .googleDriveApiService
        .files()
        .create(file)

    fun deleteFile(fileId: String): Drive.Files.Delete = apiProvider
        .googleDriveApiService
        .files()
        .delete(fileId)
}
