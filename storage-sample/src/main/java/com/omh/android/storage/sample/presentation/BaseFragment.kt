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

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.omh.android.storage.sample.presentation.util.displayToast
import com.omh.android.storage.sample.util.LOG_MESSAGE_STATE
import com.omh.android.storage.sample.util.TAG_VIEW_UPDATE

abstract class BaseFragment<ViewModel : BaseViewModel<State, Event>, State : ViewState, Event : ViewEvent> :
    Fragment() {

    abstract val viewModel: ViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.state.observe(viewLifecycleOwner) { state ->
            try {
                Log.i(TAG_VIEW_UPDATE, "$LOG_MESSAGE_STATE${state.getName()}")
                buildState(state)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        viewModel.toastMessage.observe(viewLifecycleOwner) { message ->
            displayToast(message)
        }
    }

    protected abstract fun buildState(state: State)

    protected fun dispatchEvent(event: Event) {
        viewModel.dispatchEvent(event)
    }
}
