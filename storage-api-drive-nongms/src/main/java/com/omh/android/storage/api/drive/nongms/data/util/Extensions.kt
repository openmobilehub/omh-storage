package com.omh.android.storage.api.drive.nongms.data.util

import okhttp3.ResponseBody
import java.io.ByteArrayOutputStream

fun ResponseBody?.toByteArrayOutputStream(): ByteArrayOutputStream {
    val outputStream = ByteArrayOutputStream()

    if (this == null) return outputStream

    this.byteStream().use { inputStream ->
        inputStream.copyTo(outputStream)
    }

    return outputStream
}
