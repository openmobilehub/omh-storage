package com.github.openmobilehub.storage.domain.usecase.impl

import com.github.openmobilehub.storage.domain.abstraction.FileRepository
import com.github.openmobilehub.storage.domain.usecase.file.ReadFile

class ReadFileUseCase(
    private val repository: FileRepository
) : ReadFile {
    override fun execute() {
        repository.read()
    }
}
