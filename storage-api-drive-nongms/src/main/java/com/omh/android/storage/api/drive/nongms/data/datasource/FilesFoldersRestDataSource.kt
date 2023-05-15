package com.omh.android.storage.api.drive.nongms.data.datasource

import com.omh.android.storage.api.data.datasource.network.FilesFoldersNetworkDataSource
import com.omh.android.storage.api.drive.nongms.data.GoogleStorageREST
import com.omh.android.storage.api.drive.nongms.data.mapper.fromFilesFoldersResponseListToData

internal class FilesFoldersRestDataSource(
    private val storageService: GoogleStorageREST,
) : FilesFoldersNetworkDataSource {

    override fun getAll() = fromFilesFoldersResponseListToData(
        storageService.getFilesFoldersList().execute()
    )
}
