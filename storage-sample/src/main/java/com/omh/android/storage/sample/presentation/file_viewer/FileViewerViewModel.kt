package com.omh.android.storage.sample.presentation.file_viewer

import com.omh.android.storage.api.OmhStorageClient
import com.omh.android.storage.api.domain.model.OmhFileType
import com.omh.android.storage.api.domain.usecase.GetFilesListWithParentIdUseCaseParams
import com.omh.android.storage.api.domain.usecase.OmhResult
import com.omh.android.storage.sample.domain.model.FileType
import com.omh.android.storage.sample.presentation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Stack
import javax.inject.Inject

@HiltViewModel
class FileViewerViewModel @Inject constructor(
    private val omhStorageClient: OmhStorageClient
) : BaseViewModel<FileViewerViewState, FileViewerViewEvent>() {

    companion object {
        private const val ID_ROOT = "root"

        val listOfFileTypes = listOf(
            FileType("Folder", OmhFileType.FOLDER),
            FileType("Document", OmhFileType.DOCUMENT),
            FileType("Sheet", OmhFileType.SPREADSHEET),
            FileType("Presentation", OmhFileType.PRESENTATION),
        )
    }

    var isGridLayoutManager = true
    var createFileSelectedType: OmhFileType? = null
    private val parentIdStack = Stack<String>().apply { push(ID_ROOT) }

    override fun getInitialState(): FileViewerViewState = FileViewerViewState.Initial

    override suspend fun processEvent(event: FileViewerViewEvent) {
        when (event) {
            FileViewerViewEvent.Initialize -> initializeEvent()
            is FileViewerViewEvent.RefreshFileList -> refreshFileListEvent()
            is FileViewerViewEvent.SwapLayoutManager -> swapLayoutManagerEvent()
            is FileViewerViewEvent.FileClicked -> fileClickedEvent(event)
            FileViewerViewEvent.BackPressed -> backPressedEvent()
        }
    }

    private suspend fun initializeEvent() {
        refreshFileListEvent()
    }

    private suspend fun refreshFileListEvent() {
        setState(FileViewerViewState.Loading)
        val parentId = parentIdStack.peek()

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

    private suspend fun fileClickedEvent(event: FileViewerViewEvent.FileClicked) {
        val file = event.file

        if (file.isFolder()) {
            val fileId = file.id

            parentIdStack.push(fileId)
            refreshFileListEvent()
        } else {
            // TODO: Implement download file
        }
    }

    private suspend fun backPressedEvent() {
        if (parentIdStack.peek() == ID_ROOT) {
            setState(FileViewerViewState.Finish)
        } else {
            parentIdStack.pop()
            refreshFileListEvent()
        }
    }


}
