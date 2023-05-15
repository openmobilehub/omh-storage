package com.omh.android.storage.api.domain.abstraction

import com.omh.android.storage.api.domain.model.FileOrFolder

interface FilesFoldersRepository {
    fun getAll(): List<FileOrFolder>
}
