package com.github.openmobilehub.storage.domain.abstraction

interface FolderRepository {
    fun create()
    fun read()
    fun update()
    fun delete()
    fun upload()
    fun download()
}
