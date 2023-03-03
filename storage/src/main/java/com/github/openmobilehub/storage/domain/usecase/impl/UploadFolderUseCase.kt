package com.github.openmobilehub.storage.domain.usecase.impl

import com.github.openmobilehub.storage.domain.abstraction.FolderRepository
import com.github.openmobilehub.storage.domain.usecase.folder.UploadFolder

class UploadFolderUseCase(
    private val repository: FolderRepository
) : UploadFolder {
    override fun execute() {
        repository.upload()
    }
}
