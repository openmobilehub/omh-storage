package com.github.openmobilehub.storage.domain.usecase.impl

import com.github.openmobilehub.storage.domain.abstraction.FileRepository
import com.github.openmobilehub.storage.domain.usecase.file.DeleteFile

class DeleteFileUseCase(
    private val repository: FileRepository
) : DeleteFile {
    override fun execute() {
        repository.delete()
    }
}
