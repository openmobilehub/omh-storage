package com.omh.android.storage.api.data.source

import com.omh.android.storage.api.domain.model.OmhFile

interface OmhFileRemoteDataSource {

    fun getRootFilesList(): List<OmhFile>
}
