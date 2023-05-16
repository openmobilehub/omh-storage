package com.omh.android.storage.api.domain.model

import com.omh.android.storage.api.data.mapper.FileTypeMapper

data class File(
    val mimeType: String,
    val id: String,
    val name: String,
    val modifiedTime: String
) {
    val fileType by lazy { FileTypeMapper.getFileTypeWithMime(mimeType) }

    fun isFolder() = fileType == FileType.FOLDER

    fun isFile() = !isFolder()
}
