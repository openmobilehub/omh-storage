package com.omh.android.storage.sample.presentation.file_viewer

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.omh.android.storage.api.domain.model.OmhFile
import com.omh.android.storage.sample.R
import com.omh.android.storage.sample.databinding.ActivityFileViewerBinding
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
    }

    override val viewModel: FileViewerViewModel by viewModels()
    private lateinit var binding: ActivityFileViewerBinding
    private var filesAdapter: FileAdapter? = null
    private lateinit var onBackPressedCallback: OnBackPressedCallback

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
    }

    override fun onResume() {
        super.onResume()
        with(binding) {
            swapGridOrLinearLayoutManager.setOnClickListener { dispatchEvent(FileViewerViewEvent.SwapLayoutManager) }
            createFileButton.setOnClickListener { showCreateFileDialog() }
        }
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    override fun buildState(state: FileViewerViewState) = when (state) {
        FileViewerViewState.Initial -> buildInitialState()
        FileViewerViewState.Loading -> buildLoadingState()
        is FileViewerViewState.Content -> buildContentState(state)
        is FileViewerViewState.SwapLayoutManager -> buildSwapLayoutManagerState()
        FileViewerViewState.Finish -> buildFinishState()
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

    private fun buildFinishState() = finish().also { finishAffinity() }

    private fun showCreateFileDialog() {
        val createFileDialogBuilder = AlertDialog.Builder(this).apply {
            title = getString(R.string.text_create_file_title)

            setPositiveButton("Create") { dialog, _ ->
                // TODO: dispatch event create file
                dialog.dismiss()
            }

            setNegativeButton("Cancel") { dialog, _ ->
                dialog.cancel()
            }
        }

        val createFileAlertDialog = createFileDialogBuilder.create().apply {
            setCancelable(false)
        }

        createFileAlertDialog.show()
    }
}
