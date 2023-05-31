package com.omh.android.storage.sample.presentation.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.omh.android.auth.api.OmhAuthClient
import com.omh.android.storage.sample.databinding.ActivityLoginBinding
import com.omh.android.storage.sample.presentation.splash.ActivitySplash
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ActivityLogin : AppCompatActivity() {

    private val loginLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        try {
            omhAuthClient.getAccountFromIntent(result.data)
            navigateToSplash()
        } catch (exception: Exception) {
            AlertDialog.Builder(this)
                .setTitle("An error has occurred.")
                .setMessage(exception.message)
                .setPositiveButton(android.R.string.ok) { dialog, _ ->
                    dialog.dismiss()
                }
                .create()
                .show()
        }
    }

    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    @Inject
    lateinit var omhAuthClient: OmhAuthClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.btnLogin.setOnClickListener { startLogin() }

        if (omhAuthClient.getUser() != null) {
            navigateToSplash()
        }
    }

    private fun startLogin() {
        loginLauncher.launch(omhAuthClient.getLoginIntent())
    }

    private fun navigateToSplash() {
        val intent = Intent(this, ActivitySplash::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }

}
