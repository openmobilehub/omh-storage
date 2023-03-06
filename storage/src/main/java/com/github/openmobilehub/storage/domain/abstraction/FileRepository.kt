package com.github.openmobilehub.storage.domain.abstraction

interface FileRepository {
    fun create(){
        println("" +
                "Hol"
           )
    }
    fun read()
    fun update()
    fun delete()
    fun upload()
    fun download()
}
