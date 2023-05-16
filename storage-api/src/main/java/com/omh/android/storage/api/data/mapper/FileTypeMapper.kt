package com.omh.android.storage.api.data.mapper

import com.omh.android.storage.api.domain.model.FileType

internal object FileTypeMapper {

    private val MAP_FILE_TYPES: Map<String, FileType> =
        FileType.values().associateBy { fileType -> fileType.mimeType }

    fun getFileTypeWithMime(mimeType: String): FileType {
        return if (MAP_FILE_TYPES.containsKey(mimeType)) {
            MAP_FILE_TYPES[mimeType] ?: FileType.OTHER
        } else {
            FileType.OTHER
        }
    }
}
