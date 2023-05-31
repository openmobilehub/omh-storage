package com.omh.android.storage.sample.presentation.file_viewer

import com.omh.android.storage.sample.presentation.ViewEvent

sealed class FileViewerViewEvent : ViewEvent {

    object Initialize : FileViewerViewEvent() {

        override fun getName() = "FileViewerViewEvent.Initialize"
    }

    class RefreshFileList(val parentId: String = "") : FileViewerViewEvent() {

        override fun getName() = "FileViewerViewEvent.RefreshList"
    }
}
