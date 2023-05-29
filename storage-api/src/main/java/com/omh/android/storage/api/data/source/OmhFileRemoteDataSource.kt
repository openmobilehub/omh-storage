package com.omh.android.storage.api.data.source

import com.omh.android.storage.api.domain.model.OmhFile

interface OmhFileRemoteDataSource {

    fun getFilesListWithParentId(parentId: String = "root"): List<OmhFile>

    fun createFile(name: String, mimeType: String, parentId: String?): OmhFile?
}
