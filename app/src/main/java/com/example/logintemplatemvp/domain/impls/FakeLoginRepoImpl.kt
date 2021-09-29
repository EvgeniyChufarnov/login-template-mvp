package com.example.logintemplatemvp.domain.impls

import com.example.logintemplatemvp.domain.LoginRepository
import com.example.logintemplatemvp.domain.ResultWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

private const val FAKE_DELAY = 3000L

class FakeLoginRepoImpl(
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : LoginRepository {

    override suspend fun tryToLogin(email: String, password: String) = withContext(dispatcher) {
        delay(FAKE_DELAY)
        ResultWrapper.NetworkError
    }
}