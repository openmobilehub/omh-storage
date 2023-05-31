package com.omh.android.storage.sample.presentation.drive

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.omh.android.storage.api.domain.usecase.GetFilesListWithParentIdUseCase

class FilesAndFoldersViewModelFactory(
    private val getFilesListWithParentIdUseCase: GetFilesListWithParentIdUseCase,
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(FilesAndFoldersViewModel::class.java)) {
            FilesAndFoldersViewModel(
                this.getFilesListWithParentIdUseCase
            ) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }

}
