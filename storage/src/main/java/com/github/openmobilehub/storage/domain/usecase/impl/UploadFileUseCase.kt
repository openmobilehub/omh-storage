package com.github.openmobilehub.storage.domain.usecase.impl

import com.github.openmobilehub.storage.domain.abstraction.FileRepository
import com.github.openmobilehub.storage.domain.usecase.file.UploadFile

class UploadFileUseCase(
    private val repository: FileRepository
) : UploadFile {
    override fun execute() {
        repository.upload()
    }
}
