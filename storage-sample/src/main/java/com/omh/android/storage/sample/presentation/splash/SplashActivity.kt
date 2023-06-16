package com.omh.android.storage.sample.presentation.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.omh.android.auth.api.OmhAuthClient
import com.omh.android.auth.api.OmhCredentials
import com.omh.android.storage.api.OmhStorageClient
import com.omh.android.storage.sample.databinding.ActivitySplashBinding
import com.omh.android.storage.sample.presentation.file_viewer.FileViewerActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    @Inject
    lateinit var omhAuthClient: OmhAuthClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivitySplashBinding.inflate(layoutInflater).root)
        checkUserThenNavigate()
    }

    private fun checkUserThenNavigate() {
        if (omhAuthClient.getUser() == null) {
            return
        }
        navigateToFilesAndFolders()
    }

    private fun navigateToFilesAndFolders() {
        startActivity(FileViewerActivity.getIntent(this))
    }
}
