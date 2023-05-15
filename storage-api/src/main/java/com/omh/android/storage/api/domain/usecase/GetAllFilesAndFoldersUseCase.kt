package com.omh.android.storage.api.domain.usecase

import com.omh.android.storage.api.domain.abstraction.FilesFoldersRepository

class GetAllFilesAndFoldersUseCase(
    private val repository: FilesFoldersRepository
) : GetAllFilesAndFolders {
    override fun execute() = repository.getAll()
}
