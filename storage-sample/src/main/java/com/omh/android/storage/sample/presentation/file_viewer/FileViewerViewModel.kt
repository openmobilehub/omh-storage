package com.omh.android.storage.sample.presentation.file_viewer

import com.omh.android.storage.api.domain.usecase.GetFilesListWithParentIdUseCase
import com.omh.android.storage.api.domain.usecase.GetFilesListWithParentIdUseCaseParams
import com.omh.android.storage.api.domain.usecase.OmhResult
import com.omh.android.storage.sample.di.DefaultDispatcher
import com.omh.android.storage.sample.presentation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

@HiltViewModel
class FileViewerViewModel @Inject constructor(
    private val getFilesListWithParentIdUseCase: GetFilesListWithParentIdUseCase,
) : BaseViewModel<FileViewerViewState, FileViewerViewEvent>() {

    override fun getInitialState(): FileViewerViewState = FileViewerViewState.Initial

    override suspend fun processEvent(event: FileViewerViewEvent) {
        when (event) {
            FileViewerViewEvent.Initialize -> initializeEvent()
            is FileViewerViewEvent.RefreshFileList -> refreshFileListEvent(event.parentId)
        }
    }

    private suspend fun initializeEvent() {
        refreshFileListEvent()
    }

    private suspend fun refreshFileListEvent(parentId: String = "root") {
        setState(FileViewerViewState.Loading)

        when (
            val result = getFilesListWithParentIdUseCase(GetFilesListWithParentIdUseCaseParams(parentId))
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
}
