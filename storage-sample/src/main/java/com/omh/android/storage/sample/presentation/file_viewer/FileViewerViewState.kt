package com.omh.android.storage.sample.presentation.file_viewer

import com.omh.android.storage.api.domain.model.OmhFile
import com.omh.android.storage.sample.presentation.ViewState

sealed class FileViewerViewState : ViewState {

    object Initial : FileViewerViewState() {

        override fun getName() = "FileViewerViewState.Initial"
    }

    object Loading : FileViewerViewState() {

        override fun getName() = "FileViewerViewState.Loading"
    }

    class Content(val files: List<OmhFile>) : FileViewerViewState() {

        override fun getName() = "FileViewerViewState.Content"
    }

    object SwapLayoutManager : FileViewerViewState() {

        override fun getName() = "FileViewerViewState.SwapLayoutManager"
    }
}
