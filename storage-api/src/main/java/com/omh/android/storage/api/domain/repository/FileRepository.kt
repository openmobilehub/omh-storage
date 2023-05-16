package com.omh.android.storage.api.domain.repository

interface FileRepository {
    fun create()
    fun read()
    fun update()
    fun delete()
    fun upload()
    fun download()
}
