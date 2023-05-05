package com.omh.android.storage.sample.usecase

import com.omh.android.storage.api.domain.model.FileOrFolder

interface KtsGetAllFilesAndFolders {
    suspend fun execute(): List<FileOrFolder>
}
