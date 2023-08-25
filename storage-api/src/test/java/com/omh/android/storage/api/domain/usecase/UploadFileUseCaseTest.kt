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
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Test
import java.io.File

@ExperimentalCoroutinesApi
class UploadFileUseCaseTest {
    private val repository: OmhFileRepository = mockk()

    private val uploadFileUseCase: UploadFileUseCase = UploadFileUseCase(repository)

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `given the params, when UploadFileUseCase success, then result is returned`() = runTest {
        val parentId = "510"
        val file = File("pathFile")
        val expectedResult: OmhFile = mockk()
        val params = UploadFileUseCaseParams(file, parentId)

        coEvery { repository.uploadFile(params.localFileToUpload, params.parentId) } returns expectedResult

        val result: OmhResult<UploadFileUseCaseResult> = uploadFileUseCase(params)

        Assert.assertTrue(result is OmhResult.OmhSuccess)
        TestCase.assertEquals((result as OmhResult.OmhSuccess).data.file, expectedResult)
    }

    @Test
    fun `given the params, when UploadFileUseCase fails, then a OmhError is returned`() = runTest {
        val params: UploadFileUseCaseParams = mockk()

        coEvery { repository.downloadFile(any(), any()) } throws RuntimeException()

        val result: OmhResult<UploadFileUseCaseResult> = uploadFileUseCase(params)

        assert(result is OmhResult.OmhError)
    }
}
