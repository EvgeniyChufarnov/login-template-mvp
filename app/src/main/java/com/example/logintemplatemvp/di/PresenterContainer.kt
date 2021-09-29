package com.example.logintemplatemvp.di

import com.example.logintemplatemvp.domain.impls.FakeLoginRepoImpl
import com.example.logintemplatemvp.ui.login.LoginContract
import com.example.logintemplatemvp.ui.login.LoginPresenter

class PresenterContainer {
    private val loginPresenter = LoginPresenter(FakeLoginRepoImpl())

    fun getLoginPresenter(): LoginContract.Presenter = loginPresenter
}
