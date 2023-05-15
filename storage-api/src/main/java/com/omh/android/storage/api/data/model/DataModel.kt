package com.omh.android.storage.api.data.model

interface RemoteFileOrFolder

data class RemoteFile(
    val name: String = "",
    val ext: String = "",
    val lastModDate: Long = 0L
) : RemoteFileOrFolder

data class RemoteFolder(
    val name: String = "",
    val lastModDate: Long = 0L
) : RemoteFileOrFolder
