package com.omh.android.storage.api.drive.nongms.data.source.mapper

import com.omh.android.storage.api.domain.model.OmhFile
import com.omh.android.storage.api.drive.nongms.data.source.response.FileListRemoteResponse
import com.omh.android.storage.api.drive.nongms.data.source.response.FileRemoteResponse

@SuppressWarnings("ComplexCondition")
internal fun FileRemoteResponse.toFile(): OmhFile? {
    if (mimeType == null || id == null || name == null || modifiedTime == null) {
        return null
    }

    return OmhFile(
        mimeType,
        id,
        name,
        modifiedTime
    )
}

internal fun FileListRemoteResponse.toFileList(): List<OmhFile> =
    files?.mapNotNull { remoteFileModel -> remoteFileModel?.toFile() }.orEmpty()
