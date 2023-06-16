package com.omh.android.storage.api.drive.gms.data.source.mapper

import com.google.api.services.drive.model.File
import com.omh.android.storage.api.domain.model.OmhFile

@SuppressWarnings("ComplexCondition")
fun File.toOmhFile(): OmhFile? {
    val formattedModifiedTime = modifiedTime?.toStringRfc3339().orEmpty()

    if (mimeType == null || id == null || name == null) {
        return null
    }

    val parentId = if (parents.isNullOrEmpty()) {
        ""
    } else {
        parents[0]
    }

    return OmhFile(
        mimeType,
        id,
        name,
        formattedModifiedTime,
        parentId
    )
}
