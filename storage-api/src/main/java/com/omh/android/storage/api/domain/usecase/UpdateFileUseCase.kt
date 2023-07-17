package com.omh.android.storage.api.domain.usecase

import com.omh.android.storage.api.domain.model.OmhFile
import com.omh.android.storage.api.domain.repository.OmhFileRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import java.io.File

internal class UpdateFileUseCase(
    private val repository: OmhFileRepository,
    dispatcher: CoroutineDispatcher = Dispatchers.Default
) : OmhSuspendUseCase<UpdateFileUseCaseParams, UpdateFileUseCaseResult>(dispatcher) {

    override suspend fun execute(parameters: UpdateFileUseCaseParams): UpdateFileUseCaseResult {
        return UpdateFileUseCaseResult(
            repository.updateFile(parameters.localFileToUpload, parameters.fileId)
        )
    }
}

data class UpdateFileUseCaseParams(val localFileToUpload: File, val fileId: String)

data class UpdateFileUseCaseResult(val file: OmhFile?)
