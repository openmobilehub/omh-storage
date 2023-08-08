/*
 * Copyright 2023 Open Mobile Hub
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.omh.android.storage.sample.presentation.main_activity

import android.os.Bundle
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.omh.android.auth.api.OmhAuthClient
import com.omh.android.storage.sample.R
import com.omh.android.storage.sample.databinding.ActivityBaseBinding
import com.omh.android.storage.sample.presentation.file_viewer.FileViewerFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
open class MainActivity : AppCompatActivity(), FileViewerFragment.FileViewerFragmentListener {

    private val binding: ActivityBaseBinding by lazy {
        ActivityBaseBinding.inflate(layoutInflater)
    }
    private lateinit var navController: NavController

    @Inject
    lateinit var authClient: OmhAuthClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onBackPressedDispatcher.addCallback {
            finish()
        }
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        setupNavigation()
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

    override fun finishApplication() {
        finish().also { finishAffinity() }
    }
}