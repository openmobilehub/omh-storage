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
