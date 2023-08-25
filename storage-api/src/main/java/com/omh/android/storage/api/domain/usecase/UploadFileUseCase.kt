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

package com.omh.android.storage.api.domain.usecase

import com.omh.android.storage.api.domain.model.OmhFile
import com.omh.android.storage.api.domain.repository.OmhFileRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import java.io.File

internal class UploadFileUseCase(
    private val repository: OmhFileRepository,
    dispatcher: CoroutineDispatcher = Dispatchers.Default
) : OmhSuspendUseCase<UploadFileUseCaseParams, UploadFileUseCaseResult>(dispatcher) {

    override suspend fun execute(parameters: UploadFileUseCaseParams): UploadFileUseCaseResult {
        return UploadFileUseCaseResult(
            repository.uploadFile(parameters.localFileToUpload, parameters.parentId)
        )
    }
}

data class UploadFileUseCaseParams(val localFileToUpload: File, val parentId: String?)

data class UploadFileUseCaseResult(val file: OmhFile?)
