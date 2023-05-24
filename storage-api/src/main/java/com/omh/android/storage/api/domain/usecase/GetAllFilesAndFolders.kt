package com.omh.android.storage.api.domain.usecase

import com.omh.android.storage.api.domain.model.OmhFile

interface GetAllFilesAndFolders {
    fun execute(): List<OmhFile>
}
