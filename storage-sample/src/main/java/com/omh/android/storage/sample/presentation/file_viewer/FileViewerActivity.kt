package com.omh.android.storage.sample.presentation.file_viewer

import android.Manifest
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.documentfile.provider.DocumentFile
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.omh.android.storage.api.domain.model.OmhFile
import com.omh.android.storage.sample.R
import com.omh.android.storage.sample.databinding.ActivityFileViewerBinding
import com.omh.android.storage.sample.databinding.DialogCreateFileBinding
import com.omh.android.storage.sample.databinding.DialogUploadFileBinding
import com.omh.android.storage.sample.presentation.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FileViewerActivity :
    BaseActivity<FileViewerViewModel, FileViewerViewState, FileViewerViewEvent>(),
    FileAdapter.GridItemListener {

    companion object {

        fun getIntent(context: Context) = Intent(context, FileViewerActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }

        private const val REQUEST_PERMISSIONS_CODE = 1
    }

    override val viewModel: FileViewerViewModel by viewModels()
    private lateinit var binding: ActivityFileViewerBinding
    private var filesAdapter: FileAdapter? = null
    private lateinit var onBackPressedCallback: OnBackPressedCallback
    private lateinit var filePicker: ActivityResultLauncher<String>
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<Array<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFileViewerBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                dispatchEvent(FileViewerViewEvent.BackPressed)
            }
        }

        filePicker = registerForActivityResult(
            ActivityResultContracts.GetContent()
        ) { uri: Uri? ->
            uri?.let { validUri ->
                showUploadFileDialog(validUri)
            }
        }

        requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions: Map<String, Boolean> ->
            val deniedPermissions = mutableListOf<String>()

            permissions.entries.forEach { entry: Map.Entry<String, Boolean> ->
                val permission = entry.key
                val isGranted = entry.value

                if (!isGranted) {
                    deniedPermissions.add(permission)
                }
            }

            if (deniedPermissions.isNotEmpty()) {
                requestPermissions()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.file_viewer_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.swapGridOrLinear -> {
                dispatchEvent(FileViewerViewEvent.SwapLayoutManager)
            }

            R.id.createFile -> {
                showCreateFileDialog()
            }

            R.id.uploadFile -> {
                filePicker.launch(
                    FileViewerViewModel.ANY_MIME_TYPE
                )
            }

            R.id.signOut -> {
                dispatchEvent(FileViewerViewEvent.SignOut)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun buildState(state: FileViewerViewState) = when (state) {
        FileViewerViewState.Initial -> buildInitialState()
        FileViewerViewState.Loading -> buildLoadingState()
        is FileViewerViewState.Content -> buildContentState(state)
        is FileViewerViewState.SwapLayoutManager -> buildSwapLayoutManagerState()
        FileViewerViewState.Finish -> buildFinishState()
        FileViewerViewState.CheckReadExternalStoragePermissions -> requestPermissions()
    }

    private fun buildInitialState() {
        initializeAdapter()
        dispatchEvent(FileViewerViewEvent.Initialize)
    }

    private fun buildLoadingState() = with(binding) {
        progressBar.visibility = View.VISIBLE
        noContentLayout.visibility = View.GONE
        topPanel.visibility = View.GONE
        filesRecyclerView.visibility = View.GONE
    }

    private fun buildContentState(state: FileViewerViewState.Content) {
        val files = state.files

        val (emptyFolderVisibility, recyclerVisibility) = if (files.isEmpty()) {
            Pair(View.VISIBLE, View.GONE)
        } else {
            Pair(View.GONE, View.VISIBLE)
        }

        initializeAdapter()

        with(binding) {
            progressBar.visibility = View.GONE
            noContentLayout.visibility = emptyFolderVisibility
            topPanel.visibility = recyclerVisibility
            filesRecyclerView.visibility = recyclerVisibility
        }

        filesAdapter?.submitList(files)
    }

    private fun initializeAdapter() {
        if (filesAdapter != null) {
            return
        }

        filesAdapter = FileAdapter(this, viewModel.isGridLayoutManager)
        with(binding.filesRecyclerView) {
            layoutManager = if (viewModel.isGridLayoutManager) {
                GridLayoutManager(this@FileViewerActivity, 2)
            } else {
                LinearLayoutManager(this@FileViewerActivity)
            }
            adapter = filesAdapter
        }
    }

    private fun buildSwapLayoutManagerState() {
        filesAdapter = null
        initializeAdapter()
        dispatchEvent(FileViewerViewEvent.Initialize)
    }

    override fun onFileClicked(file: OmhFile) {
        dispatchEvent(FileViewerViewEvent.FileClicked(file))
    }

    override fun onDeleteClicked(file: OmhFile) {
        dispatchEvent(FileViewerViewEvent.DeleteFile(file))
    }

    private fun buildFinishState() = finish().also { finishAffinity() }

    private fun showCreateFileDialog() {
        val dialogCreateFileView = DialogCreateFileBinding.inflate(layoutInflater)

        configureCreateFileDialogSpinner(dialogCreateFileView)

        val createFileDialogBuilder = AlertDialog.Builder(this)
            .setTitle(getString(R.string.text_create_file_title))
            .setPositiveButton("Create") { dialog, _ ->
                configureCreateFilePositiveButtonEvent(dialogCreateFileView, dialog)
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.cancel()
            }

        val createFileAlertDialog = createFileDialogBuilder.create().apply {
            setCancelable(false)
            setView(dialogCreateFileView.root)
        }

        createFileAlertDialog.show()
    }

    private fun configureCreateFilePositiveButtonEvent(
        view: DialogCreateFileBinding,
        dialog: DialogInterface
    ) {
        val fileName = view.fileName.text.toString()
        val fileType = viewModel.createFileSelectedType?.mimeType

        if (fileName.isNotBlank() && !fileType.isNullOrEmpty()) {
            dispatchEvent(FileViewerViewEvent.CreateFile(fileName, fileType))
        }

        dialog.dismiss()
    }

    private fun configureCreateFileDialogSpinner(view: DialogCreateFileBinding) {
        val fileTypes = FileViewerViewModel.listOfFileTypes

        val fileTypesSpinnerAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            fileTypes.map { fileType -> fileType.name }
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }

        with(view.fileType) {
            adapter = fileTypesSpinnerAdapter
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val fileType = fileTypes[position]
                    viewModel.createFileSelectedType = fileType.omhFileType
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    val fileType = fileTypes[0]
                    viewModel.createFileSelectedType = fileType.omhFileType
                }
            }
        }
    }

    private fun showUploadFileDialog(uri: Uri) {
        val fileName: String = DocumentFile
            .fromSingleUri(this, uri)?.name
            ?: FileViewerViewModel.DEFAULT_FILE_NAME

        val dialogUploadFileView = DialogUploadFileBinding.inflate(layoutInflater)
        dialogUploadFileView.fileName.text = fileName

        val uploadFileDialogBuilder = AlertDialog.Builder(this)
            .setTitle(getString(R.string.text_upload_file_title))
            .setPositiveButton(getString(R.string.text_upload)) { dialog, _ ->
                configureUploadFilePositiveButtonEvent(dialog, fileName, uri)
            }
            .setNegativeButton(getString(R.string.text_cancel)) { dialog, _ ->
                dialog.cancel()
            }

        val createFileAlertDialog = uploadFileDialogBuilder.create().apply {
            setCancelable(false)
            setView(dialogUploadFileView.root)
        }

        createFileAlertDialog.show()
    }

    private fun configureUploadFilePositiveButtonEvent(
        dialog: DialogInterface,
        fileName: String,
        uri: Uri
    ) {
        if (fileName.isNotBlank()) {
            dispatchEvent(FileViewerViewEvent.UploadFile(this, uri, fileName))
        }

        dialog.dismiss()
    }

    private fun requestPermissions() {
        val permissionsToRequest: Array<String> = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )

        val permissionsToAsk: Array<String> = permissionsToRequest.filter { permission: String ->
            ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED
        }.toTypedArray()

        if (permissionsToAsk.isNotEmpty()) {
            requestPermissionLauncher.launch(permissionsToAsk)
        } else {
            dispatchEvent(FileViewerViewEvent.DownloadFile)
        }
    }
}
