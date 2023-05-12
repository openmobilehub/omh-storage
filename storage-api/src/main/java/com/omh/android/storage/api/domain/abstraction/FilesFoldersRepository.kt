package com.omh.android.storage.api.domain.abstraction

import com.omh.android.storage.api.domain.model.File

interface FilesFoldersRepository {

    fun getRootFilesList(): List<File>
}
