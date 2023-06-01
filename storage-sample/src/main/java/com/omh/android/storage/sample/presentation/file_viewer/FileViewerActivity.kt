package com.omh.android.storage.sample.presentation.file_viewer

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.omh.android.storage.api.domain.model.OmhFile
import com.omh.android.storage.sample.databinding.ActivityFileViewerBinding
import com.omh.android.storage.sample.presentation.BaseActivity
import com.omh.android.storage.sample.presentation.file_viewer.adapter.FileAdapter
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFileViewerBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    override fun onResume() {
        super.onResume()
        binding.swapGridOrLinearLayoutManager.setOnClickListener { dispatchEvent(FileViewerViewEvent.SwapLayoutManager) }
    }

    override fun buildState(state: FileViewerViewState) = when (state) {
        FileViewerViewState.Initial -> buildInitialState()
        FileViewerViewState.Loading -> buildLoadingState()
        is FileViewerViewState.Content -> buildContentState(state)
        is FileViewerViewState.SwapLayoutManager -> buildSwapLayoutManagerState()
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
        // TODO: Implement click listener
    }

}
