package com.omh.android.storage.sample.presentation.main_activity

import android.content.Context
import android.content.Intent
import com.omh.android.storage.sample.presentation.BaseActivity

class MainActivity: BaseActivity() {

    companion object {

        private const val IS_USER_LOGGED_EXTRA = "isUserLogged"

        fun getIntent(context: Context, isUserLogged: Boolean) = Intent(context, MainActivity::class.java).apply {
            putExtra(IS_USER_LOGGED_EXTRA, false)
        }
    }
}