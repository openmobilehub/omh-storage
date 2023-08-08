/*
 * Copyright 2023 Open Mobile Hub
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.omh.android.storage.sample.util

import com.omh.android.storage.api.domain.model.OmhFile
import com.omh.android.storage.api.domain.model.OmhFileType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

fun CoroutineScope.launchSafe(
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> Unit
): Job {
    return this.launch(context, start) {
        try {
            block()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

private val NON_SUPPORTED_MIME_TYPES_FOR_DOWNLOAD = listOf(
    OmhFileType.THIRD_PARTY_SHORTCUT,
    OmhFileType.FILE,
    OmhFileType.FUSIONTABLE,
    OmhFileType.JAMBOARD,
    OmhFileType.MAP,
    OmhFileType.SITE,
    OmhFileType.UNKNOWN
)

fun OmhFile.isDownloadable(): Boolean = !NON_SUPPORTED_MIME_TYPES_FOR_DOWNLOAD.contains(fileType)

fun OmhFile.normalizedMimeType(): String = when (fileType) {
    OmhFileType.DOCUMENT -> OmhFileType.MICROSOFT_WORD.mimeType
    OmhFileType.DRAWING -> OmhFileType.PNG.mimeType
    OmhFileType.FORM -> OmhFileType.PDF.mimeType
    OmhFileType.PHOTO -> OmhFileType.JPEG.mimeType
    OmhFileType.PRESENTATION -> OmhFileType.MICROSOFT_POWERPOINT.mimeType
    OmhFileType.SCRIPT -> OmhFileType.JSON.mimeType
    OmhFileType.SHORTCUT -> OmhFileType.SHORTCUT.mimeType
    OmhFileType.SPREADSHEET -> OmhFileType.MICROSOFT_EXCEL.mimeType
    OmhFileType.VIDEO,
    OmhFileType.AUDIO -> OmhFileType.MP4.mimeType

    else -> this.mimeType
}

fun OmhFile.normalizeFileName(): String {
    val invalidChars = Regex("[\\\\/:*?\"'<>|]")

    var downloadName = name.replace(invalidChars, " ")

    if (downloadName.length >= 255) {
        downloadName = downloadName.substring(0..254)
    }

    return downloadName
}

fun OmhFile.getNameWithExtension(): String = if (isFileNameWithExtension(name)) {
    name
} else {
    "$name${getExtension().orEmpty()}"
}

private fun isFileNameWithExtension(fileName: String): Boolean {
    val lastDotIndex: Int = fileName.lastIndexOf('.')
    return lastDotIndex != -1 && lastDotIndex < fileName.length - 1
}
