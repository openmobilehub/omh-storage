package com.omh.android.storage.sample.drive

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.omh.android.storage.sample.databinding.ActivityFilesFoldersBinding
import com.omh.android.storage.sample.drive.adapter.grid.FilesFoldersGridAdapter

class ActivityFilesAndFolders : AppCompatActivity() {

    private lateinit var tvSortByName: TextView
    private lateinit var rvFilesAndFolders: RecyclerView
    private lateinit var rvGridAdapter: FilesFoldersGridAdapter

    private val binding: ActivityFilesFoldersBinding by lazy {
        ActivityFilesFoldersBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prepareViewBindings()
        //binding.rvEmptyView.root.visibility = View.VISIBLE
    }

    private fun prepareViewBindings() {
        setContentView(binding.root)
        tvSortByName = binding.tvSortByName
        rvFilesAndFolders = binding.rvFilesAndFolders
        prepareViews()
    }

    private fun prepareViews() {
        tvSortByName.setOnClickListener {
            // viewModel.sortByName()
        }
        rvGridAdapter = FilesFoldersGridAdapter()
        rvFilesAndFolders.setHasFixedSize(true)
        rvFilesAndFolders.layoutManager = GridLayoutManager(this, 2)
        rvFilesAndFolders.adapter = rvGridAdapter
    }

}
