package com.omh.android.storage.sample.presentation.file_viewer

import com.omh.android.storage.api.OmhStorageClient
import com.omh.android.storage.api.domain.usecase.GetFilesListWithParentIdUseCaseParams
import com.omh.android.storage.api.domain.usecase.OmhResult
import com.omh.android.storage.sample.presentation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FileViewerViewModel @Inject constructor(
    private val omhStorageClient: OmhStorageClient
) : BaseViewModel<FileViewerViewState, FileViewerViewEvent>() {

    var isGridLayoutManager = true

    override fun getInitialState(): FileViewerViewState = FileViewerViewState.Initial

    override suspend fun processEvent(event: FileViewerViewEvent) {
        when (event) {
            FileViewerViewEvent.Initialize -> initializeEvent()
            is FileViewerViewEvent.RefreshFileList -> refreshFileListEvent(event.parentId)
            is FileViewerViewEvent.SwapLayoutManager -> swapLayoutManagerEvent()
        }
    }

    private suspend fun initializeEvent() {
        refreshFileListEvent()
    }

    private suspend fun refreshFileListEvent(parentId: String = "root") {
        setState(FileViewerViewState.Loading)

        val listFiles = omhStorageClient.listFiles()

        when (
            val result = listFiles(GetFilesListWithParentIdUseCaseParams(parentId))
        ) {
            is OmhResult.OmhSuccess -> {
                setState(FileViewerViewState.Content(result.data.files))
            }

            is OmhResult.OmhError -> {
                toastMessage.postValue(result.toString())
                setState(FileViewerViewState.Content(emptyList()))
            }
        }
    }

    private fun swapLayoutManagerEvent() {
        isGridLayoutManager = !isGridLayoutManager
        setState(FileViewerViewState.SwapLayoutManager)
    }
}
