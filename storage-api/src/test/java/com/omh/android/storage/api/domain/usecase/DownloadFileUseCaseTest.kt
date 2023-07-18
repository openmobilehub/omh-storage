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
import java.io.ByteArrayOutputStream

@ExperimentalCoroutinesApi
class DownloadFileUseCaseTest {
    private val repository: OmhFileRepository = mockk()

    private val downloadFileUseCase: DownloadFileUseCase = DownloadFileUseCase(repository)

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `given the params, when DownloadFileUseCase success, then result is returned`() = runTest {
        val id = "123"
        val mimeType = "image/jpg"
        val expectedResult: ByteArrayOutputStream = mockk()
        val params = DownloadFileUseCaseParams(id, mimeType)

        coEvery { repository.downloadFile(params.fileId, params.mimeType) } returns expectedResult

        val result: OmhResult<DownloadFileUseCaseResult> = downloadFileUseCase(params)

        Assert.assertTrue(result is OmhResult.OmhSuccess)
        TestCase.assertEquals((result as OmhResult.OmhSuccess).data.outputStream, expectedResult)
    }

    @Test
    fun `given the params, when DownloadFileUseCase fails, then a OmhError is returned`() = runTest {
        val params: DownloadFileUseCaseParams = mockk()

        coEvery { repository.downloadFile(any(), any()) } throws RuntimeException()

        val result: OmhResult<DownloadFileUseCaseResult> = downloadFileUseCase(params)

        assert(result is OmhResult.OmhError)
    }
}
