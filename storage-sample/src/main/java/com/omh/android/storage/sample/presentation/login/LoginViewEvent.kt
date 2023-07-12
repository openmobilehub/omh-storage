package com.omh.android.storage.sample.presentation.login

import com.omh.android.storage.sample.presentation.ViewEvent

sealed class LoginViewEvent : ViewEvent {

    object Initialize : LoginViewEvent() {

        override fun getEventName() = "LoginViewEvent.Initialize"
    }

    object LoginClicked : LoginViewEvent() {

        override fun getEventName() = "LoginViewEvent.LoginClicked"
    }
}
