package com.omh.android.storage.api.drive.nongms.data.source.body

internal data class CreateFileRequestBody(
    val mimeType: String,
    val name: String,
    val parents: List<String> = emptyList()
)
