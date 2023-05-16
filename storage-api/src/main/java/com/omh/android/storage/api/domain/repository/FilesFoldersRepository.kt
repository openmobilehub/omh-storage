package com.omh.android.storage.api.domain.repository

import com.omh.android.storage.api.domain.model.File

interface FilesFoldersRepository {

    fun getRootFilesList(): List<File>

    fun create()

    fun open()

    fun update()

    fun delete()

    fun upload()

    fun download()
}
