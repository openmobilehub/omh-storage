package com.omh.android.storage.sample.presentation.main_activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.omh.android.storage.sample.R
import com.omh.android.storage.sample.presentation.BaseActivity
import com.omh.android.storage.sample.presentation.file_viewer.FileViewerFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity(), FileViewerFragment.FileViewerFragmentListener {

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

    override fun finishApplication() {
        finish().also { finishAffinity() }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.file_viewer_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val fileViewerFragment = getCurrentFragment() as? FileViewerFragment

        if (fileViewerFragment != null) {
            when (item.itemId) {
                R.id.swapGridOrLinear -> {
                    fileViewerFragment.swapLayout()
                }

                R.id.createFile -> {
                    fileViewerFragment.createFile()
                }

                R.id.uploadFile -> {
                    fileViewerFragment.uploadFile()
                }

                R.id.signOut -> {
                    fileViewerFragment.signOut()
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }
}