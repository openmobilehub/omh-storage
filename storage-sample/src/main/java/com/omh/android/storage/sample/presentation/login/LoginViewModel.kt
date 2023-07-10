package com.omh.android.storage.sample.presentation.login

import android.content.Intent
import com.omh.android.auth.api.OmhAuthClient
import com.omh.android.auth.api.models.OmhUserProfile
import com.omh.android.storage.sample.presentation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val omhAuthClient: OmhAuthClient
) : BaseViewModel<LoginViewState, LoginViewEvent>() {

    override fun getInitialState(): LoginViewState = LoginViewState.Initial

    override suspend fun processEvent(event: LoginViewEvent) {
        when (event) {
            LoginViewEvent.Initialize -> initializeEvent()
            LoginViewEvent.LoginClicked -> loginClickedEvent()
        }
    }

    fun getAccountFromIntent(data: Intent): OmhUserProfile =
        omhAuthClient.getAccountFromIntent(data)

    fun isUserLogged(): Boolean = omhAuthClient.getUser() != null

    fun getLoginIntent(): Intent = omhAuthClient.getLoginIntent()

    private fun initializeEvent() = Unit

    private fun loginClickedEvent() {
        setState(LoginViewState.StartLogin)
    }
}
