package com.omh.android.storage.sample.presentation.file_viewer

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.omh.android.storage.api.domain.model.OmhFile
import com.omh.android.storage.sample.databinding.ActivityFileViewerBinding
import com.omh.android.storage.sample.presentation.BaseActivity
import com.omh.android.storage.sample.presentation.file_viewer.adapter.FileGridAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FileViewerActivity :
    BaseActivity<FileViewerViewModel, FileViewerViewState, FileViewerViewEvent>(),
    FileGridAdapter.GridItemListener {

    companion object {

        fun getIntent(context: Context) = Intent(context, FileViewerActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
    }

    override val viewModel: FileViewerViewModel by viewModels()
    private lateinit var binding: ActivityFileViewerBinding
    private var adapter: FileGridAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFileViewerBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    override fun buildState(state: FileViewerViewState) {
        when (state) {
            FileViewerViewState.Initial -> buildInitialState()
            FileViewerViewState.Loading -> buildLoadingState()
            is FileViewerViewState.Content -> buildContentState(state)
        }
    }

    private fun buildInitialState() {
        initializeAdapter()
        dispatchEvent(FileViewerViewEvent.Initialize)
    }

    private fun buildLoadingState() {
        with(binding) {
            progressBar.visibility = View.VISIBLE
            noContentLayout.visibility = View.GONE
            topPanel.visibility = View.GONE
            filesRecyclerView.visibility = View.GONE
        }
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
    }

    private fun initializeAdapter() {
        if (adapter == null) {
            adapter = FileGridAdapter(this)
            with(binding.filesRecyclerView) {
                setHasFixedSize(true)
                layoutManager = GridLayoutManager(this@FileViewerActivity, 2)
                adapter = adapter
            }
        }
    }

    override fun onFileClicked(file: OmhFile) {
        // TODO: Implement click listener
    }

}
