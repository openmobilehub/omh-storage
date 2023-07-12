package com.omh.android.storage.sample.presentation.main_activity

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.omh.android.auth.api.OmhAuthClient
import com.omh.android.storage.sample.R
import com.omh.android.storage.sample.databinding.ActivityBaseBinding
import com.omh.android.storage.sample.presentation.file_viewer.FileViewerFragment
import com.omh.android.storage.sample.presentation.util.OnBackPressedListener
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
open class MainActivity : AppCompatActivity(), FileViewerFragment.FileViewerFragmentListener {

    private val binding: ActivityBaseBinding by lazy {
        ActivityBaseBinding.inflate(layoutInflater)
    }
    private lateinit var onBackPressedCallback: OnBackPressedCallback
    private lateinit var navController: NavController

    @Inject
    lateinit var authClient: OmhAuthClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        setupNavigation()

        onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                onBackPressedCallback()
            }
        }
    }

    private fun setupNavigation() {
        val navHostFragment: NavHostFragment = supportFragmentManager.findFragmentById(
            /* id = */ R.id.activity_base_fragment_container
        ) as NavHostFragment
        navController = navHostFragment.navController
        setupGraph()
        setupToolbar()
    }

    private fun setupToolbar() {
        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.file_viewer_fragment, R.id.login_fragment)
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    private fun setupGraph() {
        val navGraph = navController.navInflater.inflate(R.navigation.nav_graph)
        val startDestId = if (authClient.getUser() != null) {
            R.id.file_viewer_fragment
        } else {
            R.id.login_fragment
        }
        navGraph.setStartDestination(startDestId)
        navController.graph = navGraph
    }

    override fun onResume() {
        super.onResume()
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    override fun finishApplication() {
        finish().also { finishAffinity() }
    }

    private fun getCurrentFragment(): Fragment? {
        val navHostFragment: NavHostFragment = supportFragmentManager.findFragmentById(
            binding.activityBaseFragmentContainer.id
        ) as NavHostFragment

        return navHostFragment
            .childFragmentManager
            .fragments[0]
    }

    private fun onBackPressedCallback() {
        var eventConsumed = false
        val fragment: Fragment? = getCurrentFragment()

        if (fragment is OnBackPressedListener) {
            eventConsumed = fragment.onBackPressed()
        }

        if (eventConsumed) {
            return
        }

        onBackPressedDispatcher.onBackPressed()
    }
}