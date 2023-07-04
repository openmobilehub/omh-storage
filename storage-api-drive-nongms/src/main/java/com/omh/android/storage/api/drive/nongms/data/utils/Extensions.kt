package com.omh.android.storage.api.drive.nongms.data.utils

import okhttp3.ResponseBody
import java.io.ByteArrayOutputStream

fun ResponseBody?.toByteArrayOutputStream(): ByteArrayOutputStream {
    val outputStream = ByteArrayOutputStream()

    if (this == null) {
        return outputStream
    }

    byteStream().use { inputStream ->
        inputStream.copyTo(outputStream)
    }

    return outputStream
}