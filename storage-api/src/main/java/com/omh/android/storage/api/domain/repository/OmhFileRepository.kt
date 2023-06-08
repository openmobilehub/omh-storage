package com.omh.android.storage.api.domain.repository

import com.omh.android.storage.api.domain.model.OmhFile

interface OmhFileRepository {

    fun getFilesList(parentId: String = "root"): List<OmhFile>

    fun createFile(name: String, mimeType: String, parentId: String?): OmhFile?

    fun deleteFile(fileId: String): Boolean

    fun open()

    fun update()

    fun upload()

    fun download()
}
