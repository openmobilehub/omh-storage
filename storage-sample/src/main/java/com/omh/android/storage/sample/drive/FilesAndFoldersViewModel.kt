package com.omh.android.storage.sample.drive

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omh.android.storage.api.domain.usecase.SortFilesAndFoldersByName
import com.omh.android.storage.sample.model.UiFileFolderItemData
import com.omh.android.storage.sample.mapper.fromFilesFoldersListToUi
import com.omh.android.storage.sample.usecase.KtsGetAllFilesAndFolders
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FilesAndFoldersViewModel(
    private val getAllFilesAndFolders: KtsGetAllFilesAndFolders,
    private val sortFilesAndFoldersByName: SortFilesAndFoldersByName
) : ViewModel() {

    private val _filesFoldersLiveData = MutableLiveData<List<UiFileFolderItemData>>()
    val filesFoldersLiveData: LiveData<List<UiFileFolderItemData>> = _filesFoldersLiveData
    private val _sortByNameAscendingLiveData = MutableLiveData<Boolean>()
    val sortByNameAscendingLiveData: LiveData<Boolean> = _sortByNameAscendingLiveData

    fun loadFilesFoldersFromRoot() {
        viewModelScope.launch(Dispatchers.IO) {
            _filesFoldersLiveData.postValue(
                fromFilesFoldersListToUi(getAllFilesAndFolders.execute())
            )
        }
    }

}
