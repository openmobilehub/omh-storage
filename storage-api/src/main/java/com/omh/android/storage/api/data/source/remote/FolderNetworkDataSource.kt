package com.omh.android.storage.api.data.source.remote

interface FolderNetworkDataSource {
    fun create()
    fun read()
    fun update()
    fun delete()
    fun upload()
    fun download()
}
