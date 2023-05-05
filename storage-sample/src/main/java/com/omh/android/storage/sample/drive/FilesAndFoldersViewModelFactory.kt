package com.omh.android.storage.sample.drive

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.omh.android.storage.api.domain.usecase.SortFilesAndFoldersByName
import com.omh.android.storage.sample.usecase.KtsGetAllFilesAndFolders

class FilesAndFoldersViewModelFactory(
    private val getAllFilesAndFolders: KtsGetAllFilesAndFolders,
    private val sortFilesAndFoldersByName: SortFilesAndFoldersByName
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(FilesAndFoldersViewModel::class.java)) {
            FilesAndFoldersViewModel(
                this.getAllFilesAndFolders,
                this.sortFilesAndFoldersByName
            ) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }

}
