package com.omh.android.storage.sample.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.omh.android.auth.api.OmhAuthClient
import com.omh.android.auth.api.OmhCredentials
import com.omh.android.storage.api.OmhStorageClient
import com.omh.android.storage.sample.databinding.ActivitySplashBinding
import com.omh.android.storage.sample.drive.ActivityFilesAndFolders
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ActivitySplash : AppCompatActivity() {

    @Inject
    lateinit var omhAuthClient: OmhAuthClient

    @Inject
    lateinit var omhStorageClient: OmhStorageClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivitySplashBinding.inflate(layoutInflater).root)
        checkUserThenNavigate()
    }

    private fun checkUserThenNavigate() = lifecycleScope.launch(Dispatchers.IO) {
        if (omhAuthClient.getUser() != null) {
            setupToken()
            navigateToFilesAndFolders()
        }
    }

    private fun setupToken() = lifecycleScope.launch(Dispatchers.IO) {
        val token = when (val credentials = omhAuthClient.getCredentials()) {
            is OmhCredentials -> credentials.blockingRefreshToken()
            null -> return@launch
            else -> error("Unsupported credential type")
        }
        /*
        if (token != null) {
            omhStorageClient.setupAccessToken(token)
        }
        */
    }

    private fun navigateToFilesAndFolders() {
        val intent = Intent(this, ActivityFilesAndFolders::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }

}
