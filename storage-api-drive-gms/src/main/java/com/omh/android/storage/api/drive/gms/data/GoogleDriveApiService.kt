package com.omh.android.storage.api.drive.gms.data

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.JsonFactory
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.drive.Drive
import com.omh.android.storage.api.drive.gms.BuildConfig

internal class GoogleDriveApiService private constructor(private val credential: GoogleAccountCredential) {

    companion object {
        var instance: GoogleDriveApiService? = null

        internal fun getInstance(omhCredentials: GoogleAccountCredential): GoogleDriveApiService {
            if (instance == null) {
                instance = GoogleDriveApiService(omhCredentials)
            }

            return instance!!
        }
    }

    private val applicationName = BuildConfig.LIBRARY_PACKAGE_NAME
    private val jsonFactory: JsonFactory = GsonFactory.getDefaultInstance()
    private val httpTransport: NetHttpTransport = GoogleNetHttpTransport.newTrustedTransport()

    private val driveService: Drive by lazy { initDriveService() }

    private fun initDriveService(): Drive = Drive
        .Builder(
            httpTransport,
            jsonFactory,
            credential
        ).setApplicationName(applicationName)
        .build()

    fun getDriveService() = driveService
}
