package com.omh.android.storage.api.domain.usecase

import com.omh.android.storage.api.domain.model.OmhFile
import com.omh.android.storage.api.domain.repository.FileRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class GetRootFilesListUseCase(
    private val repository: FileRepository,
    dispatcher: CoroutineDispatcher = Dispatchers.Default
) : OmhSuspendUseCase<NoParams, GetRootFilesListUseCaseResult>(dispatcher) {

    override suspend fun execute(parameters: NoParams) = GetRootFilesListUseCaseResult(
        repository.getRootFilesList()
    )
}

data class GetRootFilesListUseCaseResult(val files: List<OmhFile>)
