package com.example.logintemplatemvp.ui.login

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.logintemplatemvp.R
import com.example.logintemplatemvp.databinding.FragmentLoginBinding
import com.example.logintemplatemvp.di.LoginApplication

class LoginFragment : Fragment(R.layout.fragment_login), LoginContract.View {
    private val binding: FragmentLoginBinding by viewBinding(FragmentLoginBinding::bind)
    private lateinit var presenter: LoginContract.Presenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initPresenter()
        presenter.attach(this)
        initButtonsListeners()
    }

    private fun initPresenter() {
        val container = (requireActivity().application as LoginApplication).presenterContainer
        presenter = container.getLoginPresenter()
    }

    private fun initButtonsListeners() {
        initSignInListener()
        initSignUpListener()
        initForgotPasswordListener()
    }

    private fun initSignInListener() {
        binding.signInButton.setOnClickListener {
            presenter.onSignInClicked(
                binding.emailTextInput.text.toString(),
                binding.passwordTextInput.text.toString()
            )
        }
    }

    private fun initSignUpListener() {
        binding.signUpButton.setOnClickListener {
            presenter.onSignUpClicked()
        }
    }

    private fun initForgotPasswordListener() {
        binding.forgotPasswordTextView.setOnClickListener {
            presenter.onForgotPasswordClicked()
        }
    }

    override fun setState(state: LoginContract.ScreenState) {
        when (state) {
            LoginContract.ScreenState.IDLE -> {
                showContent()
            }
            LoginContract.ScreenState.LOADING -> {
                showLoading()
            }
            LoginContract.ScreenState.ERROR -> {
                showContent()
            }
        }
    }

    private fun showLoading() {
        binding.contentLayout.isVisible = false
        binding.progressBar.isVisible = true
    }

    private fun showContent() {
        binding.contentLayout.isVisible = true
        binding.progressBar.isVisible = false
    }

    override fun setEmailText(email: String) {
        binding.emailTextInput.setText(email)
    }

    override fun setPasswordText(password: String) {
        binding.passwordTextInput.setText(password)
    }

    override fun showErrorMessage(errorType: LoginContract.ErrorType) {
        val message = getErrorMessageByType(errorType)

        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun getErrorMessageByType(errorType: LoginContract.ErrorType) = when (errorType) {
        LoginContract.ErrorType.CANNOT_FIND_ACCOUNT -> getString(R.string.cannot_find_account_error_message)
        LoginContract.ErrorType.CONNECTION_ERROR -> getString(R.string.connection_error_message)
        LoginContract.ErrorType.WRONG_INPUT -> getString(R.string.wrong_input_error_message)
    }

    override fun navigateToSignUpScreen() {
        (requireActivity() as Contract).navigateToSignUpScreen()
    }

    override fun navigateToForgotPasswordScreen() {
        (requireActivity() as Contract).navigateToForgotPasswordScreen()
    }

    override fun navigateOnSuccessfulLogin() {
        (requireActivity() as Contract).navigateOnSuccessfulLogin()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        presenter.onSaveState(
            binding.emailTextInput.text.toString(),
            binding.passwordTextInput.text.toString()
        )
    }

    override fun onDestroyView() {
        presenter.detach()
        super.onDestroyView()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        check(activity is Contract) { "Activity must implement LoginFragment.Contract" }
    }

    interface Contract {
        fun navigateToSignUpScreen()
        fun navigateToForgotPasswordScreen()
        fun navigateOnSuccessfulLogin()
    }
}