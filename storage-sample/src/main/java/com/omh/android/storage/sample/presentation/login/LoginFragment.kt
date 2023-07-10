package com.omh.android.storage.sample.presentation.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import com.omh.android.storage.sample.databinding.FragmentLoginBinding
import com.omh.android.storage.sample.presentation.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<LoginViewModel, LoginViewState, LoginViewEvent>() {

    companion object {

        fun newInstance() = LoginFragment()
    }

    override val viewModel: LoginViewModel by viewModels()

    private lateinit var binding: FragmentLoginBinding

    private val loginLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        context?.let { context ->
            try {
                result.data?.let { intent ->
                    viewModel.getAccountFromIntent(intent)
                    // TODO: This should dispatch an event for replace fragment, not for start an activity
                    // startActivity(FileViewerFragment.getInstance(context))
                }
            } catch (exception: Exception) {
                AlertDialog.Builder(context)
                    .setTitle("An error has occurred.")
                    .setMessage(exception.message)
                    .setPositiveButton(android.R.string.ok) { dialog, _ ->
                        dialog.dismiss()
                    }
                    .create()
                    .show()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        context?.let { context ->
            if (viewModel.isUserLogged()) {
                // TODO: this should dispatch method for replace fragment instead launch an activity
                // startActivity(FileViewerFragment.getInstance())
            }
        }
    }

    override fun buildState(state: LoginViewState) {
        when (state) {
            LoginViewState.Initial -> buildInitialState()
            LoginViewState.StartLogin -> startLogin()
        }
    }

    private fun buildInitialState() {
        binding.btnLogin.setOnClickListener { dispatchEvent(LoginViewEvent.LoginClicked) }
    }

    private fun startLogin() {
        loginLauncher.launch(viewModel.getLoginIntent())
    }
}
