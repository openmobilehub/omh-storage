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

package com.omh.android.storage.api.drive.nongms.data.source.mapper

import com.omh.android.storage.api.domain.model.OmhFile
import com.omh.android.storage.api.drive.nongms.data.source.response.FileListRemoteResponse
import com.omh.android.storage.api.drive.nongms.data.source.response.FileRemoteResponse

@SuppressWarnings("ComplexCondition")
internal fun FileRemoteResponse.toFile(): OmhFile? {
    if (mimeType == null || id == null || name == null) {
        return null
    }

    val parentId = if (parents.isNullOrEmpty()) {
        ""
    } else {
        parents[0]
    }

    return OmhFile(
        mimeType,
        id,
        name,
        modifiedTime.orEmpty(),
        parentId
    )
}

internal fun FileListRemoteResponse.toFileList(): List<OmhFile> =
    files?.mapNotNull { remoteFileModel -> remoteFileModel?.toFile() }.orEmpty()
