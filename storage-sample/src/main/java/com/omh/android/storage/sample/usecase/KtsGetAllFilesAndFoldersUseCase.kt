package com.omh.android.storage.sample.usecase

import com.omh.android.storage.api.domain.model.FileOrFolder
import com.omh.android.storage.api.domain.usecase.GetAllFilesAndFolders

class KtsGetAllFilesAndFoldersUseCase(
    private val useCase: GetAllFilesAndFolders,
) : KtsGetAllFilesAndFolders {
    override suspend fun execute(): List<FileOrFolder> = useCase.execute()
}
