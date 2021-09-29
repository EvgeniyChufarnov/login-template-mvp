package com.example.logintemplatemvp.ui.login

import com.example.logintemplatemvp.domain.LoginRepository
import com.example.logintemplatemvp.domain.ResultWrapper
import com.example.logintemplatemvp.domain.entities.UserEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class LoginPresenter(private val loginRepository: LoginRepository) : LoginContract.Presenter {
    private var view: LoginContract.View? = null
    private var state: LoginContract.ScreenState = LoginContract.ScreenState.IDLE
    private var emailInput: String? = null
    private var passwordInput: String? = null

    private val mainScope = CoroutineScope(Job() + Dispatchers.Main)

    override fun attach(view: LoginContract.View) {
        this.view = view

        view.setState(state)
        setEmailInput()
        setPasswordInput()
    }

    private fun setEmailInput() {
        emailInput?.let {
            view?.setEmailText(it)
        }
    }

    private fun setPasswordInput() {
        passwordInput?.let {
            view?.setPasswordText(it)
        }
    }


    override fun detach() {
        view = null
    }

    override fun onSignInClicked(email: String, password: String) {
        if (isValidInput(email, password)) {
            setViewState(LoginContract.ScreenState.LOADING)
            tryToLogin(email, password)
        } else {
            view?.showErrorMessage(LoginContract.ErrorType.WRONG_INPUT)
        }
    }

    private fun isValidInput(email: String, password: String): Boolean {
        return email.isNotEmpty() && password.isNotEmpty()
    }

    private fun setViewState(newState: LoginContract.ScreenState) {
        state = newState
        view?.setState(state)
    }

    private fun tryToLogin(email: String, password: String) {
        mainScope.launch {
            val result = loginRepository.tryToLogin(email, password)

            parseResult(result)
        }
    }

    private fun parseResult(result: ResultWrapper<UserEntity>) {
        when (result) {
            is ResultWrapper.Success -> {
                onSuccess()
            }
            is ResultWrapper.GenericError -> {
                onGenericError()
            }
            is ResultWrapper.NetworkError -> {
                onNetworkError()
            }
        }
    }

    private fun onSuccess() {
        setViewState(LoginContract.ScreenState.IDLE)
        view?.navigateOnSuccessfulLogin()
    }

    private fun onGenericError() {
        setViewState(LoginContract.ScreenState.ERROR)
        view?.showErrorMessage(LoginContract.ErrorType.CANNOT_FIND_ACCOUNT)
    }

    private fun onNetworkError() {
        setViewState(LoginContract.ScreenState.ERROR)
        view?.showErrorMessage(LoginContract.ErrorType.CONNECTION_ERROR)
    }

    override fun onSignUpClicked() {
        view?.navigateToSignUpScreen()
    }

    override fun onForgotPasswordClicked() {
        view?.navigateToForgotPasswordScreen()
    }

    override fun onSaveState(email: String, password: String) {
        emailInput = email
        passwordInput = password
    }
}