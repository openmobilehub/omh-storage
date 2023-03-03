package com.github.openmobilehub.storage.domain.usecase.impl

import com.github.openmobilehub.storage.domain.abstraction.FilesFoldersRepository
import com.github.openmobilehub.storage.domain.usecase.GetAllFilesAndFolders

class GetAllFilesAndFoldersUseCase(
    private val repository: FilesFoldersRepository
) : GetAllFilesAndFolders {
    override fun execute() {
        repository.getAll()
    }
}
