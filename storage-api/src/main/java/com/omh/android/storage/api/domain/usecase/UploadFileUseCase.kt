package com.omh.android.storage.api.domain.usecase

import com.omh.android.storage.api.domain.model.OmhFile
import com.omh.android.storage.api.domain.repository.OmhFileRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import java.io.File

internal class UploadFileUseCase(
    private val repository: OmhFileRepository,
    dispatcher: CoroutineDispatcher = Dispatchers.Default
) : OmhSuspendUseCase<UploadFileUseCaseParams, UploadFileUseCaseResult>(dispatcher) {

    override suspend fun execute(parameters: UploadFileUseCaseParams): UploadFileUseCaseResult {
        return UploadFileUseCaseResult(
            repository.uploadFile(parameters.localFileToUpload, parameters.parentId)
        )
    }
}

data class UploadFileUseCaseParams(val localFileToUpload: File, val parentId: String?)

data class UploadFileUseCaseResult(val file: OmhFile?)
