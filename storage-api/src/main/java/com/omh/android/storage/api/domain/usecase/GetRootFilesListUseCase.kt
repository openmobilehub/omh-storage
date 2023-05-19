package com.omh.android.storage.api.domain.usecase

import com.omh.android.storage.api.domain.repository.FileRepository

class GetRootFilesListUseCase(
    private val repository: FileRepository
) : OmhSuspendUseCase {
    override fun execute() = repository.getRootFilesList()
}
