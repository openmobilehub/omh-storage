package com.omh.android.storage.api.domain.usecase

import com.omh.android.storage.api.domain.model.File

interface GetAllFilesAndFolders {
    fun execute(): List<File>
}
