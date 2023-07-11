package com.omh.android.storage.sample.presentation.main_activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.omh.android.storage.sample.R
import com.omh.android.storage.sample.presentation.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    companion object {

        private const val IS_USER_LOGGED_EXTRA = "isUserLogged"
        private const val IS_USER_LOGGED_EXTRA_DEFAULT_VALUE = false

        fun getIntent(context: Context, isUserLogged: Boolean) =
            Intent(context, MainActivity::class.java).apply {
                putExtra(IS_USER_LOGGED_EXTRA, isUserLogged)
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val isUserLogged = intent.getBooleanExtra(
            IS_USER_LOGGED_EXTRA,
            IS_USER_LOGGED_EXTRA_DEFAULT_VALUE
        )

        navigateTo(
            if (isUserLogged) {
                R.id.action_login_fragment_to_file_viewer_fragment
            } else {
                R.id.login_fragment
            }
        )
    }
}