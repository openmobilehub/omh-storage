package com.omh.android.storage.api.domain.usecase

import com.omh.android.storage.api.domain.model.FileOrFolder

interface GetAllFilesAndFolders {
    fun execute(): List<FileOrFolder>
}
