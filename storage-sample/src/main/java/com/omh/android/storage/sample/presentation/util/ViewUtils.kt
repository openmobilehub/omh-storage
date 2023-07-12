package com.omh.android.storage.sample.presentation.util

import android.view.View
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment

fun View.displayToast(message: String, duration: Int = Toast.LENGTH_LONG) {
    Toast.makeText(this.context, message, duration).show()
}

fun Fragment.displayToast(message: String, duration: Int = Toast.LENGTH_LONG) {
    view?.displayToast(message, duration)
}

fun Fragment.navigateTo(@IdRes resId: Int) = NavHostFragment
    .findNavController(this)
    .navigate(resId)
