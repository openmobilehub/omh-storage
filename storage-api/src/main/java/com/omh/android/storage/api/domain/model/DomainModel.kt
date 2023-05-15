package com.omh.android.storage.api.domain.model

interface FileOrFolder {
    val name: String
    val modificationDate: Long
}

data class File(
    override val name: String,
    val extension: String,
    override val modificationDate: Long
) : FileOrFolder

data class Folder(
    override val name: String,
    override val modificationDate: Long
) : FileOrFolder
