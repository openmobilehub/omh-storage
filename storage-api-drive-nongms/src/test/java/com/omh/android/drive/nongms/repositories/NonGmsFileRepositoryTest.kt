package com.omh.android.drive.nongms.repositories

import com.omh.android.storage.api.data.source.OmhFileRemoteDataSource
import com.omh.android.storage.api.domain.model.OmhFile
import com.omh.android.storage.api.domain.model.OmhStorageException
import com.omh.android.storage.api.drive.nongms.data.repository.NonGmsFileRepositoryImpl
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.io.ByteArrayOutputStream
import java.io.File

internal class NonGmsFileRepositoryTest {

    companion object {
        private const val NAME = "file.txt"
        private const val MIME_TYPE = "text/plain"
        private const val PARENT_ID = "510"
        private const val FILE_ID = "123"
        private const val FILE_PATH = "anyPath"
    }

    @MockK(relaxed = true)
    private lateinit var dataSource: OmhFileRemoteDataSource

    private lateinit var fileRepositoryImpl: NonGmsFileRepositoryImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        fileRepositoryImpl = NonGmsFileRepositoryImpl(dataSource)
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

        assertEquals(expectedResult, result)
        verify { dataSource.getFilesList(PARENT_ID) }
    }

    @Test
    fun `given the information of a new file, when createFile is success, then a OmhFile is returned`() {
        val expectedFile: OmhFile = mockk()

        every { dataSource.createFile(any(), any(), any()) } returns expectedFile

        val result = fileRepositoryImpl.createFile(NAME, MIME_TYPE, PARENT_ID)

        assertEquals(expectedFile, result)
        verify { dataSource.createFile(NAME, MIME_TYPE, PARENT_ID) }
    }

    @Test
    fun `given a fileId, when deleteFile is success, then true is returned`() {
        every { dataSource.deleteFile(any()) } returns true

        val result = fileRepositoryImpl.deleteFile(FILE_ID)

        assertTrue(result)
        verify { dataSource.deleteFile(FILE_ID) }
    }

    @Test
    fun `given a File and a parentId, when uploadFile is success, then a OmhFile is returned`() {
        val localFileUpload = File(FILE_PATH)
        val expectedFile: OmhFile = mockk()

        every { dataSource.uploadFile(any(), any()) } returns expectedFile

        val result = fileRepositoryImpl.uploadFile(localFileUpload, PARENT_ID)

        assertEquals(expectedFile, result)
        verify { dataSource.uploadFile(localFileUpload, PARENT_ID) }
    }

    @Test
    fun `given a file id and a mime type, when downloadFile is success, then a ByteArrayOutputStream is returned`() {
        val expectedFile: ByteArrayOutputStream = mockk()

        every { dataSource.downloadFile(any(), any()) } returns expectedFile

        val result = fileRepositoryImpl.downloadFile(FILE_ID, MIME_TYPE)

        assertEquals(expectedFile, result)
        verify { dataSource.downloadFile(FILE_ID, MIME_TYPE) }
    }

    @Test
    fun `given a File and a file id, when updateFile is success, then a OmhFile is returned`() {
        val localFileUpload = File(FILE_PATH)
        val expectedFile: OmhFile = mockk()

        every { dataSource.updateFile(any(), any()) } returns expectedFile

        val result = fileRepositoryImpl.updateFile(localFileUpload, FILE_ID)

        assertEquals(expectedFile, result)
        verify { dataSource.updateFile(localFileUpload, FILE_ID) }
    }

    @Test
    fun `given a parentId, when getFileList fails, then returns an empty list`() {
        every { dataSource.getFilesList(any()) } returns emptyList()

        val result = fileRepositoryImpl.getFilesList(PARENT_ID)

        assertTrue(result.isEmpty())
        verify { dataSource.getFilesList(PARENT_ID) }
    }

    @Test
    fun `given a name, mimeType and a parentId, when createFile fails, then returns null`() {
        every { dataSource.createFile(any(), any(), any()) } returns null

        val result = fileRepositoryImpl.createFile(NAME, MIME_TYPE, PARENT_ID)

        assertNull(result)
        verify { dataSource.createFile(NAME, MIME_TYPE, PARENT_ID) }
    }

    @Test
    fun `given a fileId, when deleteFile fails, then false is returned`() {
        every { dataSource.deleteFile(any()) } returns false

        val result = fileRepositoryImpl.deleteFile(FILE_ID)

        assertFalse(result)
        verify { dataSource.deleteFile(FILE_ID) }
    }

    @Test
    fun `given a File and a parentId, when uploadFile fails, then null is returned`() {
        val localFileUpload = File(FILE_PATH)

        every { dataSource.uploadFile(any(), any()) } returns null

        val result = fileRepositoryImpl.uploadFile(localFileUpload, PARENT_ID)

        assertNull(result)
        verify { dataSource.uploadFile(localFileUpload, PARENT_ID) }
    }

    @Test(expected = OmhStorageException.DownloadException::class)
    fun `given a null mimeType, when downloadFile fails, then a DownloadException is thrown`() {
        val expectedFile: OmhStorageException.DownloadException = mockk()

        every { dataSource.downloadFile(any(), any()) } throws expectedFile

        fileRepositoryImpl.downloadFile(FILE_ID, MIME_TYPE)
    }

    @Test(expected = OmhStorageException.DownloadException::class)
    fun `given a fileId and mimeType, when downloadFile fails, then a DownloadException is thrown`() {
        val expectedFile: OmhStorageException.DownloadException = mockk()

        every { dataSource.downloadFile(any(), any()) } throws expectedFile

        fileRepositoryImpl.downloadFile(FILE_ID, MIME_TYPE)
    }

    @Test(expected = OmhStorageException.UpdateException::class)
    fun `given a File and a file id, when updateFile fails, then a UpdateException is thrown`() {
        val localFileUpload = File(FILE_PATH)
        val expectedFile: OmhStorageException.UpdateException = mockk()

        every { dataSource.updateFile(any(), any()) } throws expectedFile

        fileRepositoryImpl.updateFile(localFileUpload, FILE_ID)
    }
}
