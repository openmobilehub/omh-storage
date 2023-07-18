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

@ExperimentalCoroutinesApi
class DeleteFileUseCaseTest {
    private val repository: OmhFileRepository = mockk()

    private val deleteFileUseCase: DeleteFileUseCase = DeleteFileUseCase(repository)

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `given the params, when DeleteFileUseCase success, then OmhSuccess is returned`() = runTest {
        val id = "123"
        val params = DeleteFileUseCaseParams(id)

        coEvery { repository.deleteFile(params.fileId) } returns true

        val result: OmhResult<DeleteFileUseCaseResult> = deleteFileUseCase(params)

        Assert.assertTrue(result is OmhResult.OmhSuccess)
        TestCase.assertTrue((result as OmhResult.OmhSuccess).data.isSuccess)
    }

    @Test
    fun `given the params, when DeleteFileUseCase fails, then a OmhError is returned`() = runTest {
        val params: DeleteFileUseCaseParams = mockk()

        coEvery { repository.deleteFile(any()) } throws RuntimeException()

        val result: OmhResult<DeleteFileUseCaseResult> = deleteFileUseCase(params)

        assert(result is OmhResult.OmhError)
    }
}
