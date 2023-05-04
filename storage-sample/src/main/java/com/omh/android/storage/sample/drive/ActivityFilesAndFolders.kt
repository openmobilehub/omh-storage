package com.omh.android.storage.sample.drive

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.omh.android.storage.sample.databinding.ActivityFilesFoldersBinding

class ActivityFilesAndFolders : AppCompatActivity() {

    private val binding: ActivityFilesFoldersBinding by lazy {
        ActivityFilesFoldersBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.recvEmptyView.root.visibility = View.VISIBLE
    }

}
