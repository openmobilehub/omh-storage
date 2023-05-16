package com.omh.android.storage.api.domain.model

interface FileOrFolder {
    val name: String
    val modificationDate: Long
}

data class Folder(
    override val name: String,
    override val modificationDate: Long
) : FileOrFolder
