package com.omh.android.storage.api.data.mapper

import com.omh.android.storage.api.data.model.RemoteFile
import com.omh.android.storage.api.data.model.RemoteFileOrFolder
import com.omh.android.storage.api.data.model.RemoteFolder
import com.omh.android.storage.api.domain.model.File
import com.omh.android.storage.api.domain.model.FileOrFolder
import com.omh.android.storage.api.domain.model.Folder

fun fromFilesFoldersRemoteListToDomain(inputList: List<RemoteFileOrFolder>): List<FileOrFolder> {
    val outputList = mutableListOf<FileOrFolder>()
    if (inputList.isEmpty()) {
        return outputList
    }
    for (gDriveFileOrFolder in inputList) {
        outputList.add(
            if (gDriveFileOrFolder is RemoteFile) {
                File(
                    gDriveFileOrFolder.name,
                    gDriveFileOrFolder.ext,
                    gDriveFileOrFolder.lastModDate
                )
            } else {
                Folder(
                    (gDriveFileOrFolder as RemoteFolder).name,
                    gDriveFileOrFolder.lastModDate
                )
            }
        )
    }
    return outputList
}
