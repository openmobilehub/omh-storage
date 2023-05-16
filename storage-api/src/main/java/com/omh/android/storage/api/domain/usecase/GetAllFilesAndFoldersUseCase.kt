package com.omh.android.storage.api.domain.usecase

import com.omh.android.storage.api.domain.repository.FileRepository

class GetAllFilesAndFoldersUseCase(
    private val repository: FileRepository
) : GetAllFilesAndFolders {
    override fun execute() = repository.getRootFilesList()
}
