package com.omh.android.storage.sample.presentation.main_activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.omh.android.auth.api.OmhAuthClient
import com.omh.android.storage.sample.R
import com.omh.android.storage.sample.presentation.BaseActivity
import com.omh.android.storage.sample.presentation.file_viewer.FileViewerFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity(), FileViewerFragment.FileViewerFragmentListener {

    @Inject
    lateinit var authClient: OmhAuthClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val screenId = if (authClient.getUser() != null) {
            R.id.action_login_fragment_to_file_viewer_fragment
        } else {
            R.id.login_fragment
        }
        navigateTo(screenId)
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
                R.id.swapGridOrLinear -> fileViewerFragment.swapLayout()
                R.id.createFile -> fileViewerFragment.createFile()
                R.id.uploadFile -> fileViewerFragment.uploadFile()
                R.id.signOut -> fileViewerFragment.signOut()
            }
        }

        return super.onOptionsItemSelected(item)
    }
}