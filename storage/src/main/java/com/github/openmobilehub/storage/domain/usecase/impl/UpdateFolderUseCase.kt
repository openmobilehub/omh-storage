package com.github.openmobilehub.storage.domain.usecase.impl

import com.github.openmobilehub.storage.domain.abstraction.FolderRepository
import com.github.openmobilehub.storage.domain.usecase.folder.UpdateFolder

class UpdateFolderUseCase(
    private val repository: FolderRepository
) : UpdateFolder {
    override fun execute() {
        repository.update()
    }
}
