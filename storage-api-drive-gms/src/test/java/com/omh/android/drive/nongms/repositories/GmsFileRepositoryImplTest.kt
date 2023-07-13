package com.omh.android.drive.nongms.repositories

import com.omh.android.storage.api.data.source.OmhFileRemoteDataSource
import com.omh.android.storage.api.domain.model.OmhFile
import com.omh.android.storage.api.drive.gms.data.repository.GmsFileRepositoryImpl
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.io.ByteArrayOutputStream
import java.io.File

internal class GmsFileRepositoryImplTest {

    companion object {
        private const val NAME = "file.txt"
        private const val MIME_TYPE = "text/plain"
        private const val PARENT_ID = "510"
        private const val FILE_ID = "123"
        private const val FILE_PATH = "anyPath"
    }

    @MockK(relaxed = true)
    private lateinit var dataSource: OmhFileRemoteDataSource

    private lateinit var fileRepositoryImpl: GmsFileRepositoryImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        fileRepositoryImpl = GmsFileRepositoryImpl(dataSource)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `given a parentId, when getFileList is success, then a list of OmhFiles is returned`() {
        val expectedResult: List<OmhFile> = mockk()

        every { dataSource.getFilesList(any()) } returns expectedResult

        val result = fileRepositoryImpl.getFilesList(PARENT_ID)

        TestCase.assertEquals(expectedResult, result)
        verify { dataSource.getFilesList(PARENT_ID) }
    }

    @Test
    fun `given the information of a new file, when createFile is success, then a OmhFile is returned`() {
        val expectedFile: OmhFile = mockk()

        every { dataSource.createFile(any(), any(), any()) } returns expectedFile

        val result = fileRepositoryImpl.createFile(NAME, MIME_TYPE, PARENT_ID)

        TestCase.assertEquals(expectedFile, result)
        verify { dataSource.createFile(NAME, MIME_TYPE, PARENT_ID) }
    }

    @Test
    fun `given a fileId, when deleteFile is success, then true is returned`() {
        every { dataSource.deleteFile(any()) } returns true

        val result = fileRepositoryImpl.deleteFile(FILE_ID)

        TestCase.assertTrue(result)
        verify { dataSource.deleteFile(FILE_ID) }
    }

    @Test
    fun `given a File and a parentId, when uploadFile is success, then a OmhFile is returned`() {
        val localFileUpload = File(FILE_PATH)
        val expectedFile: OmhFile = mockk()

        every { dataSource.uploadFile(any(), any()) } returns expectedFile

        val result = fileRepositoryImpl.uploadFile(localFileUpload, PARENT_ID)

        TestCase.assertEquals(expectedFile, result)
        verify { dataSource.uploadFile(localFileUpload, PARENT_ID) }
    }

    @Test
    fun `given a file id and a mime type, when downloadFile is success, then a ByteArrayOutputStream is returned`() {
        val expectedFile: ByteArrayOutputStream = mockk()

        every { dataSource.downloadFile(any(), any()) } returns expectedFile

        val result = fileRepositoryImpl.downloadFile(FILE_ID, MIME_TYPE)

        TestCase.assertEquals(expectedFile, result)
        verify { dataSource.downloadFile(FILE_ID, MIME_TYPE) }
    }

    @Test
    fun `given a File and a file id, when updateFile is success, then a OmhFile is returned`() {
        val localFileUpload = File(FILE_PATH)
        val expectedFile: OmhFile = mockk()

        every { dataSource.updateFile(any(), any()) } returns expectedFile

        val result = fileRepositoryImpl.updateFile(localFileUpload, FILE_ID)

        TestCase.assertEquals(expectedFile, result)
        verify { dataSource.updateFile(localFileUpload, FILE_ID) }
    }
}
