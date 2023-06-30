package com.omh.android.storage.sample.presentation.splash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.omh.android.auth.api.OmhAuthClient
import com.omh.android.storage.sample.databinding.ActivitySplashBinding
import com.omh.android.storage.sample.presentation.file_viewer.FileViewerActivity
import com.omh.android.storage.sample.presentation.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    @Inject
    lateinit var omhAuthClient: OmhAuthClient

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(ActivitySplashBinding.inflate(layoutInflater).root)
    }

    override fun onResume() {
        super.onResume()

        startActivity(
            if (omhAuthClient.getUser() == null) {
                LoginActivity.getIntent(this)
            } else {
                FileViewerActivity.getIntent(this)
            }
        )
    }
}
