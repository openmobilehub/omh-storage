package com.github.openmobilehub.storage.domain.usecase.impl

import com.github.openmobilehub.storage.domain.abstraction.FileRepository
import com.github.openmobilehub.storage.domain.usecase.file.DownloadFile

class DownloadFileUseCase(
    private val repository: FileRepository
) : DownloadFile {
    override fun execute() {
        repository.download()
    }
}
