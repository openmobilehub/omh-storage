package com.omh.android.storage.sample.presentation.file_viewer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omh.android.storage.api.domain.model.OmhFile
import com.omh.android.storage.api.domain.usecase.GetFilesListWithParentIdUseCase
import com.omh.android.storage.api.domain.usecase.GetFilesListWithParentIdUseCaseParams
import com.omh.android.storage.api.domain.usecase.OmhResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FileViewerViewModel @Inject constructor(
    private val getFilesListWithParentIdUseCase: GetFilesListWithParentIdUseCase
) : ViewModel() {

    private val _filesFoldersLiveData = MutableLiveData<List<OmhFile>>()
    val filesFoldersLiveData: LiveData<List<OmhFile>> = _filesFoldersLiveData
    private val _sortByNameAscendingLiveData = MutableLiveData<Boolean>()
    val sortByNameAscendingLiveData: LiveData<Boolean> = _sortByNameAscendingLiveData

    fun loadFilesFoldersFromRoot() {
        viewModelScope.launch(Dispatchers.IO) {

            when (val result = getFilesListWithParentIdUseCase(GetFilesListWithParentIdUseCaseParams())) {
                is OmhResult.OmhSuccess -> {
                    _filesFoldersLiveData.postValue(result.data.files)
                }
                is OmhResult.OmhError -> {
                    // TODO: handle exception
                }
            }
        }
    }

}
