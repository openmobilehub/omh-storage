package com.omh.android.storage.api.drive.nongms.data.source.mapper

import com.omh.android.storage.api.domain.model.File
import com.omh.android.storage.api.drive.nongms.data.source.response.FileListRemoteResponse
import com.omh.android.storage.api.drive.nongms.data.source.response.FileRemoteResponse

@SuppressWarnings("ComplexCondition")
internal fun FileRemoteResponse.toFile(): File? {
    if (mimeType == null || id == null || name == null || modifiedTime == null) {
        return null
    }

    return File(
        mimeType,
        id,
        name,
        modifiedTime
    )
}

internal fun FileListRemoteResponse.toFileList(): List<File> =
    files?.mapNotNull { remoteFileModel -> remoteFileModel?.toFile() }.orEmpty()
