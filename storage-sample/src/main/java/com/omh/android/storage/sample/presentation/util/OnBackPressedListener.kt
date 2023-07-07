package com.omh.android.storage.sample.presentation.util

interface OnBackPressedListener {

    /**
     * @return true if there's an event consumed, false otherwise
     */
    fun onBackPressed(): Boolean
}