/*
 * Copyright 2023 Open Mobile Hub
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.omh.android.storage.sample.presentation.file_viewer

import android.content.Context
import android.net.Uri
import com.omh.android.storage.api.domain.model.OmhFile
import com.omh.android.storage.sample.presentation.ViewEvent

sealed class FileViewerViewEvent : ViewEvent {

    object Initialize : FileViewerViewEvent() {

        override fun getEventName() = "FileViewerViewEvent.Initialize"
    }

    object RefreshFileList : FileViewerViewEvent() {

        override fun getEventName() = "FileViewerViewEvent.RefreshList"
    }

    object SwapLayoutManager : FileViewerViewEvent() {

        override fun getEventName() = "FileViewerViewEvent.SwapLayoutManager"
    }

    class FileClicked(val file: OmhFile) : FileViewerViewEvent() {

        override fun getEventName() = "FileViewerViewEvent.FileClicked"
    }

    object DownloadFile : FileViewerViewEvent() {

        override fun getEventName() = "FileViewerViewEvent.DownloadFile"
    }

    object BackPressed : FileViewerViewEvent() {

        override fun getEventName() = "FileViewerViewEvent.BackPressed"
    }

    class CreateFile(val name: String, val mimeType: String) : FileViewerViewEvent() {

        override fun getEventName() = "FileViewerViewEvent.CreateFile"
    }

    class DeleteFile(val file: OmhFile) : FileViewerViewEvent() {

        override fun getEventName() = "FileViewerViewEvent.CreateFile"
    }

    class UploadFile(val context: Context, val uri: Uri, val fileName: String) : FileViewerViewEvent() {

        override fun getEventName() = "FileViewerViewEvent.UploadFile"
    }

    object SignOut : FileViewerViewEvent() {

        override fun getEventName() = "FileViewerViewEvent.SignOut"
    }

    class UpdateFileClicked(val file: OmhFile) : FileViewerViewEvent() {

        override fun getEventName() = "FileViewerViewEvent.UpdateFileClicked"
    }

    class UpdateFile(val context: Context, val uri: Uri, val fileName: String) : FileViewerViewEvent() {

        override fun getEventName() = "FileViewerViewEvent.UpdateFile"
    }
}
