package com.omh.android.storage.sample.presentation.login

import com.omh.android.storage.sample.presentation.ViewState

sealed class LoginViewState : ViewState {

    object Initial : LoginViewState() {

        override fun getName() = "LoginViewState.Initial"
    }

    object StartLogin : LoginViewState() {

        override fun getName() = "LoginViewState.StartLogin"
    }
}
