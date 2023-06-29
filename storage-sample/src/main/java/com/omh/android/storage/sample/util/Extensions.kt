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
    when (this.mimeType) {
        OmhFileType.DOCUMENT.mimeType -> OmhFileType.MICROSOFT_WORD.mimeType
        OmhFileType.DRAWING.mimeType -> OmhFileType.PNG.mimeType
        OmhFileType.FOLDER.mimeType -> OmhFileType.ZIP.mimeType
        OmhFileType.FORM.mimeType -> OmhFileType.PDF.mimeType
        OmhFileType.PHOTO.mimeType -> OmhFileType.JPEG.mimeType
        OmhFileType.PRESENTATION.mimeType -> OmhFileType.MICROSOFT_POWERPOINT.mimeType
        OmhFileType.SCRIPT.mimeType -> OmhFileType.JSON.mimeType
        OmhFileType.SHORTCUT.mimeType -> OmhFileType.SHORTCUT.mimeType
        OmhFileType.SPREADSHEET.mimeType -> OmhFileType.MICROSOFT_EXCEL.mimeType
        OmhFileType.VIDEO.mimeType,
        OmhFileType.AUDIO.mimeType -> OmhFileType.MP4.mimeType
        else -> this.mimeType
    }