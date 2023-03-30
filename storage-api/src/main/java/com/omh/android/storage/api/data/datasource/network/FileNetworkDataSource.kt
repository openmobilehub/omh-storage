package com.omh.android.storage.api.data.datasource.network

interface FileNetworkDataSource {
    fun create()
    fun read()
    fun update()
    fun delete()
    fun upload()
    fun download()
}
