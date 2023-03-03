package com.github.openmobilehub.storage.domain.usecase.impl

import com.github.openmobilehub.storage.domain.abstraction.FolderRepository
import com.github.openmobilehub.storage.domain.usecase.folder.CreateFolder

class CreateFolderUseCase(
    private val repository: FolderRepository
) : CreateFolder {
    override fun execute() {
        repository.create()
    }
}
