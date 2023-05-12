package com.omh.android.storage.api.drive.nongms.data.source.mapper

import com.omh.android.storage.api.domain.model.File
import com.omh.android.storage.api.drive.nongms.data.source.response.FileListRemoteResponse
import com.omh.android.storage.api.drive.nongms.data.source.response.FileRemoteResponse

@SuppressWarnings("ComplexCondition")
internal fun FileRemoteResponse.toFileResponse() =
    if (mimeType == null || id == null || name == null || modifiedTime == null) {
        null
    } else {
        File(
            mimeType,
            id,
            name,
            modifiedTime
        )
    }

internal fun FileListRemoteResponse.toFileResponseList() =
    files?.mapNotNull { it?.toFileResponse() }.orEmpty()
