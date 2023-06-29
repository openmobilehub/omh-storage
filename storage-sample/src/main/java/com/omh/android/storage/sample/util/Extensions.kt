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

fun OmhFile.getDownloadableMimeType() =
    when (fileType) {
        OmhFileType.DOCUMENT -> OmhFileType.MICROSOFT_WORD.mimeType
        OmhFileType.DRAWING -> OmhFileType.PNG.mimeType
        OmhFileType.FOLDER -> OmhFileType.ZIP.mimeType
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