package com.omh.android.storage.sample.mapper

import com.omh.android.storage.api.domain.model.File
import com.omh.android.storage.api.domain.model.FileOrFolder
import com.omh.android.storage.api.domain.model.Folder
import com.omh.android.storage.sample.model.UiFileFolderItemData
import com.omh.android.storage.sample.model.UiFileItemData
import com.omh.android.storage.sample.model.UiFolderItemData
import java.text.SimpleDateFormat
import java.util.Locale

private const val RESPONSE_DATE_FORMAT = "MMM dd"

fun fromFilesFoldersListToUi(inputList: List<FileOrFolder>): List<UiFileFolderItemData> {
    val outputList = mutableListOf<UiFileFolderItemData>()
    if (inputList.isEmpty()) return outputList

    for (item in inputList) {
        outputList.add(
            if (item is File) UiFileItemData(
                item.name,
                item.extension,
                SimpleDateFormat(RESPONSE_DATE_FORMAT, Locale.ENGLISH).format(item.modificationDate)
                //item.lastModDate.toString()
            ) else UiFolderItemData(
                (item as Folder).name,
                SimpleDateFormat(
                    RESPONSE_DATE_FORMAT,
                    Locale.ENGLISH
                ).format(item.modificationDate * 1000L)
                //item.lastModDate.toString()
            )
        )
    }
    return outputList
}
