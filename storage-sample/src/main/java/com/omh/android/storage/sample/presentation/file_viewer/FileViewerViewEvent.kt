package com.omh.android.storage.sample.presentation.file_viewer

import android.content.Context
import android.net.Uri
import com.omh.android.storage.api.domain.model.OmhFile
import com.omh.android.storage.sample.presentation.ViewEvent

sealed class FileViewerViewEvent : ViewEvent {

    object Initialize : FileViewerViewEvent() {

        override fun getEventName() = "FileViewerViewEvent.Initialize"
    }

    object RefreshFileList : FileViewerViewEvent() {

        override fun getEventName() = "FileViewerViewEvent.RefreshList"
    }

    object SwapLayoutManager : FileViewerViewEvent() {

        override fun getEventName() = "FileViewerViewEvent.SwapLayoutManager"
    }

    class FileClicked(val file: OmhFile) : FileViewerViewEvent() {

        override fun getEventName() = "FileViewerViewEvent.FileClicked"
    }

    object BackPressed : FileViewerViewEvent() {

        override fun getEventName() = "FileViewerViewEvent.BackPressed"
    }

    class CreateFile(val name: String, val mimeType: String) : FileViewerViewEvent() {

        override fun getEventName() = "FileViewerViewEvent.CreateFile"
    }

    class DeleteFile(val file: OmhFile) : FileViewerViewEvent() {

        override fun getEventName() = "FileViewerViewEvent.CreateFile"
    }

    class UploadFile(val context: Context, val uri: Uri, val fileName: String) : FileViewerViewEvent() {

        override fun getEventName() = "FileViewerViewEvent.UploadFile"
    }
}
