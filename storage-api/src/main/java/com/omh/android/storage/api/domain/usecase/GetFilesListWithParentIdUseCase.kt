package com.omh.android.storage.api.domain.usecase

import com.omh.android.storage.api.domain.model.OmhFile
import com.omh.android.storage.api.domain.repository.FileRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class GetFilesListWithParentIdUseCase(
    private val repository: FileRepository,
    dispatcher: CoroutineDispatcher = Dispatchers.Default
) : OmhSuspendUseCase<GetFilesListWithParentIdUseCaseParams, GetFilesListWithParentIdUseCaseResult>(dispatcher) {

    override suspend fun execute(
        parameters: GetFilesListWithParentIdUseCaseParams
    ) = GetFilesListWithParentIdUseCaseResult(
        repository.getFilesListWithParentId(parameters.parentId)
    )
}

data class GetFilesListWithParentIdUseCaseParams(val parentId: String = "root")

data class GetFilesListWithParentIdUseCaseResult(val files: List<OmhFile>)
