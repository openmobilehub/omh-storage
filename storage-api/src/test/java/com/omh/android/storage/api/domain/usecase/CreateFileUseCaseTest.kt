package com.omh.android.storage.api.domain.usecase

import android.util.Log
import com.omh.android.storage.api.domain.model.OmhFile
import com.omh.android.storage.api.domain.repository.OmhFileRepository
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class CreateFileUseCaseTest {

    private val repository: OmhFileRepository = mockk()

    private val createFileUseCase: CreateFileUseCase = CreateFileUseCase(repository)

    @Before
    fun prepareEnvironment() {
        mockkStatic(Log::class)
        every { Log.v(any(), any()) } returns 0
        every { Log.d(any(), any()) } returns 0
        every { Log.i(any(), any()) } returns 0
        every { Log.e(any(), any()) } returns 0
    }

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

        val expectedFile: OmhFile = mockk()

        every { expectedFile.name } returns name
        every { expectedFile.mimeType } returns mimeType
        every { expectedFile.parentId } returns parentId
        every { expectedFile.id } returns id
        every { expectedFile.modifiedTime } returns modifiedTime

        coEvery { repository.createFile(params.name, params.mimeType, params.parentId) } returns expectedFile

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
