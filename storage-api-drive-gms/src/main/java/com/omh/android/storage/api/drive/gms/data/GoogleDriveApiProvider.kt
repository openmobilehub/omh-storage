package com.omh.android.storage.api.drive.gms.data

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.JsonFactory
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.drive.Drive
import com.omh.android.storage.api.drive.gms.BuildConfig

internal class GoogleDriveApiProvider private constructor(private val credential: GoogleAccountCredential) {

    companion object {
        private var instance: GoogleDriveApiProvider? = null

        internal fun getInstance(omhCredentials: GoogleAccountCredential): GoogleDriveApiProvider {
            if (instance == null) {
                instance = GoogleDriveApiProvider(omhCredentials)
            }

            return instance!!
        }
    }

    private val applicationName = BuildConfig.LIBRARY_PACKAGE_NAME
    private val jsonFactory: JsonFactory = GsonFactory.getDefaultInstance()
    private val httpTransport: NetHttpTransport = GoogleNetHttpTransport.newTrustedTransport()

    internal val googleDriveApiService: Drive by lazy { initDriveService() }

    private fun initDriveService(): Drive = Drive
        .Builder(
            httpTransport,
            jsonFactory,
            credential
        ).setApplicationName(applicationName)
        .build()
}
