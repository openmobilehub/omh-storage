package com.omh.android.storage.sample.presentation.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.omh.android.auth.api.OmhAuthClient
import com.omh.android.storage.sample.databinding.ActivityLoginBinding
import com.omh.android.storage.sample.presentation.file_viewer.FileViewerActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    companion object {

        fun getIntent(context: Context) = Intent(context, LoginActivity::class.java)
    }

    @Inject
    lateinit var omhAuthClient: OmhAuthClient

    private val loginLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        try {
            omhAuthClient.getAccountFromIntent(result.data)
            startActivity(FileViewerActivity.getIntent(this))
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

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnLogin.setOnClickListener { startLogin() }
    }

    override fun onResume() {
        super.onResume()

        if (omhAuthClient.getUser() != null) {
            startActivity(FileViewerActivity.getIntent(this))
        }
    }

    private fun startLogin() {
        loginLauncher.launch(omhAuthClient.getLoginIntent())
    }
}
