package com.github.openmobilehub.storage.domain.usecase.impl

import com.github.openmobilehub.storage.domain.abstraction.FolderRepository
import com.github.openmobilehub.storage.domain.usecase.folder.DeleteFolder

class DeleteFolderUseCase(
    private val repository: FolderRepository
) : DeleteFolder {
    override fun execute() {
        repository.delete()
    }
}
