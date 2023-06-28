package com.omh.android.storage.sample.presentation.file_viewer

import android.content.Context
import android.net.Uri
import com.omh.android.auth.api.OmhAuthClient
import com.omh.android.storage.api.OmhStorageClient
import com.omh.android.storage.api.domain.model.OmhFile
import com.omh.android.storage.api.domain.model.OmhFileType
import com.omh.android.storage.sample.domain.model.FileType
import com.omh.android.storage.sample.presentation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.File
import java.util.Stack
import javax.inject.Inject

@HiltViewModel
class FileViewerViewModel @Inject constructor(
    private val omhStorageClient: OmhStorageClient,
    private val authClient: OmhAuthClient
) : BaseViewModel<FileViewerViewState, FileViewerViewEvent>() {

    companion object {
        private const val ID_ROOT = "root"

        val listOfFileTypes = listOf(
            FileType("Folder", OmhFileType.FOLDER),
            FileType("Document", OmhFileType.DOCUMENT),
            FileType("Sheet", OmhFileType.SPREADSHEET),
            FileType("Presentation", OmhFileType.PRESENTATION),
        )

        const val ANY_MIME_TYPE = "*/*"
        const val DEFAULT_FILE_NAME = "Untitled"
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
            is FileViewerViewEvent.UploadFile -> uploadFile(event)
            FileViewerViewEvent.SignOut -> signOut()
        }
    }

    private fun initializeEvent() {
        refreshFileListEvent()
    }

    private fun refreshFileListEvent() {
        setState(FileViewerViewState.Loading)
        val parentId = parentIdStack.peek()

        val cancellable = omhStorageClient.listFiles(parentId)
            .addOnSuccess { data ->
                val files: List<OmhFile> = data.files
                setState(FileViewerViewState.Content(files))
            }
            .addOnFailure { e ->
                toastMessage.postValue(e.message)
                setState(FileViewerViewState.Content(emptyList()))
            }
            .execute()
        cancellableCollector.addCancellable(cancellable)
    }

    private fun swapLayoutManagerEvent() {
        isGridLayoutManager = !isGridLayoutManager
        setState(FileViewerViewState.SwapLayoutManager)
    }

    private fun fileClickedEvent(event: FileViewerViewEvent.FileClicked) {
        val file = event.file

        if (file.isFolder()) {
            val fileId = file.id

            parentIdStack.push(fileId)
            refreshFileListEvent()
        } else {
            // TODO: Implement download file
        }
    }

    private fun backPressedEvent() {
        if (parentIdStack.peek() == ID_ROOT) {
            setState(FileViewerViewState.Finish)
        } else {
            parentIdStack.pop()
            refreshFileListEvent()
        }
    }

    private fun createFileEvent(event: FileViewerViewEvent.CreateFile) {
        setState(FileViewerViewState.Loading)
        val parentId = parentIdStack.peek()

        val cancellable = omhStorageClient.createFile(event.name, event.mimeType, parentId)
            .addOnSuccess {
                refreshFileListEvent()
            }
            .addOnFailure { e ->
                toastMessage.postValue(e.message)
                refreshFileListEvent()
            }
            .execute()
        cancellableCollector.addCancellable(cancellable)
    }

    private fun uploadFile(event: FileViewerViewEvent.UploadFile) {
        setState(FileViewerViewState.Loading)

        val parentId = parentIdStack.peek()
        val filePath = getFile(event.context, event.uri, event.fileName)

        val cancellable = omhStorageClient.uploadFile(filePath, parentId)
            .addOnSuccess { result ->
                val resultMessage = if (result.file == null) {
                    "${event.fileName} was NOT uploaded"
                } else {
                    "${event.fileName} was successfully uploaded"
                }

                toastMessage.postValue(resultMessage)

                refreshFileListEvent()
            }
            .addOnFailure { e ->
                toastMessage.postValue("ERROR: ${event.fileName} was NOT uploaded")
                e.printStackTrace()

                refreshFileListEvent()
            }
            .execute()

        cancellableCollector.addCancellable(cancellable)
    }

    private fun getFile(context: Context, uri: Uri, fileName: String): File {
        val tempFile = File(context.cacheDir, fileName)

        context.contentResolver.openInputStream(uri)?.use { inputStream ->
            tempFile.outputStream().use { output ->
                inputStream.copyTo(output)
            }
        }

        return tempFile
    }

    private fun deleteFileEvent(event: FileViewerViewEvent.DeleteFile) {
        setState(FileViewerViewState.Loading)

        val file = event.file

        val cancellable = omhStorageClient.deleteFile(file.id)
            .addOnSuccess { data ->
                handleDeleteSuccess(data.isSuccess, file)

                refreshFileListEvent()
            }
            .addOnFailure {
                toastMessage.postValue("ERROR: ${file.name} was NOT deleted")

                refreshFileListEvent()
            }
            .execute()
        cancellableCollector.addCancellable(cancellable)
    }

    private fun handleDeleteSuccess(isSuccessful: Boolean, file: OmhFile) {
        val toastText = if (isSuccessful) {
            "${file.name} was successfully deleted"
        } else {
            "${file.name} was NOT deleted"
        }

        toastMessage.postValue(toastText)
    }

    private fun signOut() {
        val cancellable = authClient.signOut()
            .addOnSuccess { setState(FileViewerViewState.Finish) }
            .addOnFailure { setState(FileViewerViewState.Finish) }
            .execute()

        cancellableCollector.addCancellable(cancellable)
    }
}
