package com.github.openmobilehub.storage.domain.usecase.impl

import com.github.openmobilehub.storage.domain.abstraction.FolderRepository
import com.github.openmobilehub.storage.domain.usecase.folder.DownloadFolder

class DownloadFolderUseCase(
    private val repository: FolderRepository
) : DownloadFolder {
    override fun execute() {
        repository.download()
    }
}
