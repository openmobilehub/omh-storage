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

package com.omh.android.storage.sample.presentation

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omh.android.auth.api.async.CancellableCollector
import com.omh.android.storage.sample.util.LOG_MESSAGE_EVENT
import com.omh.android.storage.sample.util.TAG_VIEW_UPDATE
import com.omh.android.storage.sample.util.launchSafe

abstract class BaseViewModel<State : ViewState, Event : ViewEvent> : ViewModel() {

    val state: MutableLiveData<State> = MutableLiveData()
    val toastMessage: MutableLiveData<String?> = MutableLiveData()
    val errorDialogMessage: MutableLiveData<String?> = MutableLiveData()
    protected val cancellableCollector = CancellableCollector()

    init {
        setInitialState()
    }

    protected abstract fun getInitialState(): State

    private fun setInitialState() {
        state.value = getInitialState()
    }

    fun getCurrentState() = state.value ?: getInitialState()

    fun dispatchEvent(event: Event) {
        viewModelScope.launchSafe {
            Log.i(TAG_VIEW_UPDATE, "$LOG_MESSAGE_EVENT${event.getEventName()}")
            processEvent(event)
        }
    }

    protected abstract suspend fun processEvent(event: Event)

    protected fun setState(state: State) {
        this.state.postValue(state)
    }

    override fun onCleared() {
        super.onCleared()
        cancellableCollector.clear()
    }
}
