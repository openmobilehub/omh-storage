package com.omh.android.storage.sample.presentation.file_viewer

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.omh.android.storage.api.domain.model.OmhFile
import com.omh.android.storage.sample.databinding.ActivityFilesFoldersBinding
import com.omh.android.storage.sample.presentation.file_viewer.adapter.FileGridAdapter

class FileViewerActivity : AppCompatActivity(), FileGridAdapter.GridItemListener{

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
