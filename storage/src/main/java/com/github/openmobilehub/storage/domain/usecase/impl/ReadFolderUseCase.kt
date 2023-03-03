package com.github.openmobilehub.storage.domain.usecase.impl

import com.github.openmobilehub.storage.domain.abstraction.FolderRepository
import com.github.openmobilehub.storage.domain.usecase.folder.ReadFolder

class ReadFolderUseCase(
    private val repository: FolderRepository
) : ReadFolder {
    override fun execute() {
        repository.read()
    }
}
