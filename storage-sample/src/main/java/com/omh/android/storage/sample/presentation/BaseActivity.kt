package com.omh.android.storage.sample.presentation

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.omh.android.storage.sample.databinding.ActivityBaseBinding
import com.omh.android.storage.sample.presentation.util.OnBackPressedListener

abstract class BaseActivity : AppCompatActivity() {

    protected lateinit var binding: ActivityBaseBinding
    private lateinit var onBackPressedCallback: OnBackPressedCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBaseBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        onBackPressedCallback = object : OnBackPressedCallback(true) {

            override fun handleOnBackPressed() {
                onBackPressedCallback()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    fun addFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .add(
                binding.activityBaseFragmentContainer.id,
                fragment
            )
            .commit()
    }

    protected fun getCurrentFragment(): Fragment? = supportFragmentManager
        .findFragmentById(
            binding.activityBaseFragmentContainer.id
        )

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
