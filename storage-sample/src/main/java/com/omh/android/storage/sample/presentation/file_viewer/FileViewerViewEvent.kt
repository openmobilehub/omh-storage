package com.omh.android.storage.sample.presentation.file_viewer

import com.omh.android.storage.api.domain.model.OmhFile
import com.omh.android.storage.sample.presentation.ViewEvent

sealed class FileViewerViewEvent : ViewEvent {

    object Initialize : FileViewerViewEvent() {

        override fun getName() = "FileViewerViewEvent.Initialize"
    }

    object RefreshFileList : FileViewerViewEvent() {

        override fun getName() = "FileViewerViewEvent.RefreshList"
    }

    object SwapLayoutManager : FileViewerViewEvent() {

        override fun getName() = "FileViewerViewEvent.SwapLayoutManager"
    }

    class FileClicked(val file: OmhFile) : FileViewerViewEvent() {

        override fun getName() = "FileViewerViewEvent.FileClicked"
    }

    object BackPressed : FileViewerViewEvent() {

        override fun getName() = "FileViewerViewEvent.BackPressed"
    }
}
