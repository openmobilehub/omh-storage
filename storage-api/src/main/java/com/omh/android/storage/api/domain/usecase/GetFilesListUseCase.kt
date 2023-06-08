package com.omh.android.storage.api.domain.usecase

import com.omh.android.storage.api.domain.model.OmhFile
import com.omh.android.storage.api.domain.repository.OmhFileRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class GetFilesListUseCase(
    private val repository: OmhFileRepository,
    dispatcher: CoroutineDispatcher = Dispatchers.Default
) : OmhSuspendUseCase<GetFilesListUseCaseParams, GetFilesListUseCaseResult>(dispatcher) {

    override suspend fun execute(
        parameters: GetFilesListUseCaseParams
    ) = GetFilesListUseCaseResult(
        repository.getFilesList(parameters.parentId)
    )
}

data class GetFilesListUseCaseParams(val parentId: String = "root")

data class GetFilesListUseCaseResult(val files: List<OmhFile>)
