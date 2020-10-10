package com.softwareDrivingNetwork.sdn.core.repo

import com.softwareDrivingNetwork.sdn.core.api.LoginApi
import com.softwareDrivingNetwork.sdn.models.login.SignInBody

class LoginRepo(var loginApi: LoginApi) {

    suspend fun signIn(signInBody: SignInBody) = loginApi.signIn(signInBody)

    suspend fun getUserById(signInBody: String) = loginApi.getUserData(signInBody)


    suspend fun resetPassword(signInBody: SignInBody) = loginApi.resetPassword(signInBody)
    suspend fun get(signInBody: SignInBody) = loginApi.getvehicle(signInBody)

}

