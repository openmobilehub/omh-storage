package com.omh.android.storage.api.domain.usecase

import com.omh.android.storage.api.domain.model.OmhFile
import com.omh.android.storage.api.domain.repository.FileRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class CreateFileUseCase(
    private val repository: FileRepository,
    dispatcher: CoroutineDispatcher = Dispatchers.Default
) : OmhSuspendUseCase<CreateFileUseCaseParams, CreateFileUseCaseResult>(dispatcher) {

    override suspend fun execute(parameters: CreateFileUseCaseParams) = CreateFileUseCaseResult(
        repository.createFile(parameters.name, parameters.mimeType, parameters.parentId)
    )
}

data class CreateFileUseCaseParams(val name: String, val mimeType: String, val parentId: String?)

data class CreateFileUseCaseResult(val file: OmhFile?)
