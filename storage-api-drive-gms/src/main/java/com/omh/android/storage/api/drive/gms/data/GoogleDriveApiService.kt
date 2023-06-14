package com.omh.android.storage.api.drive.gms.data

import com.google.api.services.drive.Drive

internal class GoogleDriveApiService(private val apiProvider: GoogleDriveApiProvider) {

    fun getFilesList(): Drive.Files.List = apiProvider.getDriveApiService().files().list()
}
