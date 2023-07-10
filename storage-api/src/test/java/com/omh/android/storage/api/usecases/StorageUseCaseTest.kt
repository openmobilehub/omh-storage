package com.omh.android.storage.api.usecases

import com.omh.android.storage.api.domain.model.OmhFile
import com.omh.android.storage.api.domain.repository.OmhFileRepository
import com.omh.android.storage.api.domain.usecase.CreateFileUseCase
import com.omh.android.storage.api.domain.usecase.CreateFileUseCaseParams
import com.omh.android.storage.api.domain.usecase.CreateFileUseCaseResult
import com.omh.android.storage.api.domain.usecase.OmhResult
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class StorageUseCaseTest {

    @MockK(relaxed = true)
    private lateinit var repository: OmhFileRepository
    private lateinit var useCase: CreateFileUseCase
    private val params = CreateFileUseCaseParams("fileName", "image/jpg", "510")

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        useCase = CreateFileUseCase(repository)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `given the params, when CreateFileUseCase success, then result is returned`() = runTest {
        val expectedFile: OmhFile = mockk()
        val expectedResult: OmhResult.OmhSuccess<CreateFileUseCaseResult> =
            OmhResult.OmhSuccess(CreateFileUseCaseResult(expectedFile))

        coEvery { repository.createFile(any(), any(), any()) } returns expectedFile

        val result: OmhResult<CreateFileUseCaseResult> = useCase(params)

        coVerify { repository.createFile("fileName", "image/jpg", "510") }
        confirmVerified(repository)
        assertEquals(result, expectedResult)
    }

    @Test
    fun `given the params, when  CreateFileUseCase fails, then an Exception is thrown`() = runTest {
        coEvery { repository.createFile(any(), any(), any()) } throws Exception()

        try {
            useCase(params)
        } catch (exception: Exception) {
            assertTrue(exception is Exception)
        }
    }
}
