package com.omh.android.storage.sample.model

interface UiFileFolderItemData

class UiFileItemData(
    val name: String = "",
    val ext: String = "",
    val lastModDate: String = ""
) : UiFileFolderItemData {
    companion object {
        internal const val RV_TYPE = 1
    }
}

class UiFolderItemData(
    val name: String = "",
    val lastModDate: String = "",
) : UiFileFolderItemData {
    companion object {
        internal const val RV_TYPE = 2
    }
}
