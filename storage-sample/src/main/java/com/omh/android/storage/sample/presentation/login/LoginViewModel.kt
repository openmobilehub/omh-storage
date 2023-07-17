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
