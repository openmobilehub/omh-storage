package com.omh.android.storage.sample.presentation.file_viewer

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.omh.android.storage.api.domain.model.OmhFile
import com.omh.android.storage.sample.databinding.ActivityFilesFoldersBinding
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

    private lateinit var tvSortByName: TextView
    private lateinit var rvFilesAndFolders: RecyclerView
    private lateinit var rvGridAdapter: FileGridAdapter

    private val binding: ActivityFilesFoldersBinding by lazy {
        ActivityFilesFoldersBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prepareViewBindings()
        binding.rvEmptyView.root.visibility = View.VISIBLE
        binding.tvSortByName.visibility = View.GONE
    }

    override fun buildState(state: FileViewerViewState) {
        TODO("Not yet implemented")
    }

    private fun prepareViewBindings() {
        setContentView(binding.root)
        /*
        tvSortByName = binding.tvSortByName
        rvFilesAndFolders = binding.rvFilesAndFolders
        prepareViews()
        */
    }

    private fun prepareViews() {
        tvSortByName.setOnClickListener {
            // viewModel.sortByName()
        }
        rvGridAdapter = FileGridAdapter(this)
        rvFilesAndFolders.setHasFixedSize(true)
        rvFilesAndFolders.layoutManager = GridLayoutManager(this, 2)
        rvFilesAndFolders.adapter = rvGridAdapter
    }

    override fun onFileClicked(file: OmhFile) {
        // TODO: Implement click listener
    }

}
