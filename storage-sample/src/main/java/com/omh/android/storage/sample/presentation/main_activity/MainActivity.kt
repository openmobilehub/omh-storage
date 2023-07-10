package com.omh.android.storage.sample.presentation.main_activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.omh.android.storage.sample.presentation.BaseActivity
import com.omh.android.storage.sample.presentation.file_viewer.FileViewerFragment
import com.omh.android.storage.sample.presentation.login.LoginFragment

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

        addFragment(
            if (isUserLogged) {
                FileViewerFragment.newInstance()
            } else {
                LoginFragment.newInstance()
            }
        )
    }
}