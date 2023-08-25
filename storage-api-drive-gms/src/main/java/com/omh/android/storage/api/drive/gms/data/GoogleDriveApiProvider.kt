/*
 * Copyright 2023 Open Mobile Hub
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.omh.android.storage.api.drive.gms.data

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.JsonFactory
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.drive.Drive
import com.omh.android.storage.api.drive.gms.BuildConfig

internal class GoogleDriveApiProvider private constructor(internal val credential: GoogleAccountCredential) {

    companion object {
        private var instance: GoogleDriveApiProvider? = null

        internal fun getInstance(newCred: GoogleAccountCredential): GoogleDriveApiProvider {
            val oldCred: GoogleAccountCredential? = instance?.credential
            val isDifferentAccount = oldCred?.selectedAccountName != newCred.selectedAccountName
            if (instance == null || isDifferentAccount) {
                instance = GoogleDriveApiProvider(newCred)
            }

            return instance!!
        }
    }

    private val applicationName = BuildConfig.LIBRARY_PACKAGE_NAME
    private val jsonFactory: JsonFactory = GsonFactory.getDefaultInstance()
    private val httpTransport: NetHttpTransport = GoogleNetHttpTransport.newTrustedTransport()

    internal val googleDriveApiService: Drive by lazy { initDriveService() }

    private fun initDriveService(): Drive = Drive
        .Builder(httpTransport, jsonFactory, credential)
        .setApplicationName(applicationName)
        .build()
}
