package com.example.logintemplatemvp.ui.login

class LoginContract {
    enum class ScreenState {
        IDLE, LOADING, ERROR
    }

    enum class ErrorType {
        CANNOT_FIND_ACCOUNT, CONNECTION_ERROR, WRONG_INPUT
    }

    interface View {
        fun setState(state: ScreenState)
        fun setEmailText(email: String)
        fun setPasswordText(password: String)
        fun showErrorMessage(errorType: ErrorType)
        fun navigateToSignUpScreen()
        fun navigateToForgotPasswordScreen()
        fun navigateOnSuccessfulLogin()
    }

    interface Presenter {
        fun attach(view: View)
        fun detach()
        fun onSignInClicked(email: String, password: String)
        fun onSignUpClicked()
        fun onForgotPasswordClicked()
        fun onSaveState(email: String, password: String)
    }
}