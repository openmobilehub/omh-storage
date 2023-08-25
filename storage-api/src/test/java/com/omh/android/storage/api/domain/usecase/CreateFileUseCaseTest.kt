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
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Test

@ExperimentalCoroutinesApi
class CreateFileUseCaseTest {

    private val repository: OmhFileRepository = mockk()

    private val createFileUseCase: CreateFileUseCase = CreateFileUseCase(repository)

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `given the params, when CreateFileUseCase success, then a OmhSuccess is returned`() = runTest {
        val name = "fileName"
        val mimeType = "image/jpg"
        val parentId = "510"
        val id = "123"
        val modifiedTime = "2023-07-04T03:03:55.397Z"
        val params = CreateFileUseCaseParams(name, mimeType, parentId)

        val expectedFile = OmhFile(mimeType, id, name, modifiedTime, parentId)

        coEvery {
            repository.createFile(params.name, params.mimeType, params.parentId)
        } returns expectedFile

        val result: OmhResult<CreateFileUseCaseResult> = createFileUseCase(params)

        assertTrue(result is OmhResult.OmhSuccess)
        assertEquals((result as OmhResult.OmhSuccess).data.file, expectedFile)
    }

    @Test
    fun `given the params, when CreateFileUseCase fails, then a OmhError is returned`() = runTest {
        val params: CreateFileUseCaseParams = mockk()

        coEvery {
            repository.createFile(any(), any(), any())
        } throws RuntimeException()

        val result: OmhResult<CreateFileUseCaseResult> = createFileUseCase(params)

        assert(result is OmhResult.OmhError)
    }
}
