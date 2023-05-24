package com.omh.android.storage.api.domain.repository

import com.omh.android.storage.api.domain.model.OmhFile

interface FileRepository {

    fun getRootFilesList(): List<OmhFile>

    fun createFile(name: String, mimeType: String): OmhFile?

    fun open()

    fun update()

    fun delete()

    fun upload()

    fun download()
}
