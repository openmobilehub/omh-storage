package com.omh.android.storage.sample.presentation.file_viewer

import com.omh.android.storage.api.OmhStorageClient
import com.omh.android.storage.api.domain.model.OmhFileType
import com.omh.android.storage.api.domain.usecase.CreateFileUseCase
import com.omh.android.storage.api.domain.usecase.CreateFileUseCaseParams
import com.omh.android.storage.api.domain.usecase.DeleteFileUseCase
import com.omh.android.storage.api.domain.usecase.DeleteFileUseCaseParams
import com.omh.android.storage.api.domain.usecase.GetFilesListUseCase
import com.omh.android.storage.api.domain.usecase.GetFilesListUseCaseParams
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
            is FileViewerViewEvent.CreateFile -> createFileEvent(event)
            is FileViewerViewEvent.DeleteFile -> deleteFileEvent(event)
        }
    }

    private suspend fun initializeEvent() {
        refreshFileListEvent()
    }

    private suspend fun refreshFileListEvent() {
        setState(FileViewerViewState.Loading)
        val parentId = parentIdStack.peek()

        val listFilesUseCase: GetFilesListUseCase = omhStorageClient.listFiles()

        when (
            val result = listFilesUseCase(GetFilesListUseCaseParams(parentId))
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

    private suspend fun createFileEvent(event: FileViewerViewEvent.CreateFile) {
        setState(FileViewerViewState.Loading)
        val parentId = parentIdStack.peek()

        val createFileUseCase: CreateFileUseCase = omhStorageClient.createFile()

        when (
            val result =
                createFileUseCase(CreateFileUseCaseParams(event.name, event.mimeType, parentId))
        ) {
            is OmhResult.OmhSuccess -> {
                refreshFileListEvent()
            }

            is OmhResult.OmhError -> {
                toastMessage.postValue(result.toString())
                refreshFileListEvent()
            }
        }
    }

    private suspend fun deleteFileEvent(event: FileViewerViewEvent.DeleteFile) {
        setState(FileViewerViewState.Loading)

        val file = event.file

        val deleteFileUseCase: DeleteFileUseCase = omhStorageClient.deleteFile()

        when (val result = deleteFileUseCase(DeleteFileUseCaseParams(file.id))) {
            is OmhResult.OmhSuccess -> {
                toastMessage.postValue(
                    if (result.data.isSuccess) {
                        "${file.name} was successfully deleted"
                    } else {
                        "${file.name} was NOT deleted"
                    }
                )
                refreshFileListEvent()
            }

            is OmhResult.OmhError -> {
                toastMessage.postValue("ERROR: ${file.name} was NOT deleted")
                refreshFileListEvent()
            }
        }
    }
}
