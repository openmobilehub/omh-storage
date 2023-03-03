package com.github.openmobilehub.storage.domain.usecase.impl

import com.github.openmobilehub.storage.domain.abstraction.FileRepository
import com.github.openmobilehub.storage.domain.usecase.file.CreateFile

class CreateFileUseCase(
    private val repository: FileRepository
) : CreateFile {
    override fun execute() {
        repository.create()
    }
}
