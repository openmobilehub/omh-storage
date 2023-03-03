package com.github.openmobilehub.storage.domain.usecase.impl

import com.github.openmobilehub.storage.domain.abstraction.FileRepository
import com.github.openmobilehub.storage.domain.usecase.file.UpdateFile

class UpdateFileUseCase(
    private val repository: FileRepository
) : UpdateFile {
    override fun execute() {
        repository.update()
    }
}
