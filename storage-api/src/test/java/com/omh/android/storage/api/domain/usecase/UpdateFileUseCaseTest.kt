package com.omh.android.storage.api.domain.usecase

import android.util.Log
import com.omh.android.storage.api.domain.model.OmhFile
import com.omh.android.storage.api.domain.repository.OmhFileRepository
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import junit.framework.TestCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.io.File

@ExperimentalCoroutinesApi
class UpdateFileUseCaseTest {
    private val repository: OmhFileRepository = mockk()
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default

    private val updateFileUseCase: UpdateFileUseCase = UpdateFileUseCase(repository, dispatcher)

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
    fun `given the params, when UpdateFileUseCase success, then result is returned`() = runTest {
        val id = "123"
        val file = File("pathFile")
        val params = UpdateFileUseCaseParams(file, id)
        val expectedResult: OmhFile = mockk()

        coEvery { repository.updateFile(params.localFileToUpload, params.fileId) } returns expectedResult

        val result: OmhResult<UpdateFileUseCaseResult> = updateFileUseCase(params)

        Assert.assertTrue(result is OmhResult.OmhSuccess)
        TestCase.assertEquals((result as OmhResult.OmhSuccess).data.file, expectedResult)
    }

    @Test
    fun `given the params, when UpdateFileUseCase fails, then a OmhError is returned`() = runTest {
        val params: UpdateFileUseCaseParams = mockk()

        coEvery { repository.downloadFile(any(), any()) } throws RuntimeException()

        val result: OmhResult<UpdateFileUseCaseResult> = updateFileUseCase(params)

        assert(result is OmhResult.OmhError)
    }
}
