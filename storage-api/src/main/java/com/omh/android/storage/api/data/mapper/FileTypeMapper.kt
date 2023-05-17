package com.omh.android.storage.api.data.mapper

import com.omh.android.storage.api.domain.model.OmhFileType

internal object FileTypeMapper {

    private val MAP_FILE_TYPES: Map<String, OmhFileType> =
        OmhFileType.values().associateBy { fileType -> fileType.mimeType }

    fun getFileTypeWithMime(mimeType: String): OmhFileType {
        return if (MAP_FILE_TYPES.containsKey(mimeType)) {
            MAP_FILE_TYPES[mimeType] ?: OmhFileType.OTHER
        } else {
            OmhFileType.OTHER
        }
    }
}
