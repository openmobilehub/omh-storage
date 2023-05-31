package com.omh.android.storage.api.domain.repository

import com.omh.android.storage.api.domain.model.OmhFile

interface FileRepository {

    fun getFilesListWithParentId(parentId: String = "root"): List<OmhFile>

    fun createFile(name: String, mimeType: String, parentId: String?): OmhFile?

    fun open()

    fun update()

    fun delete()

    fun upload()

    fun download()
}
