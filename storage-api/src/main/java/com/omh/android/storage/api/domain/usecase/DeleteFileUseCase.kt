package com.omh.android.storage.api.domain.usecase

import com.omh.android.storage.api.domain.repository.OmhFileRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

internal class DeleteFileUseCase(
    private val repository: OmhFileRepository,
    dispatcher: CoroutineDispatcher = Dispatchers.Default
) : OmhSuspendUseCase<DeleteFileUseCaseParams, DeleteFileUseCaseResult>(dispatcher) {

    override suspend fun execute(parameters: DeleteFileUseCaseParams) = DeleteFileUseCaseResult(
        repository.deleteFile(parameters.fileId)
    )
}

data class DeleteFileUseCaseParams(val fileId: String)

data class DeleteFileUseCaseResult(val isSuccess: Boolean)
