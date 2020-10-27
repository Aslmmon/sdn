package com.softwareDrivingNetwork.sdn.core.repo

import android.util.Log
import com.softwareDrivingNetwork.sdn.common.saveUserData
import com.softwareDrivingNetwork.sdn.common.stringify
import com.softwareDrivingNetwork.sdn.core.api.LoginApi
import com.softwareDrivingNetwork.sdn.models.User
import com.softwareDrivingNetwork.sdn.models.login.SignInBody

class LoginRepo(var loginApi: LoginApi) {

    //    suspend fun signIn(signInBody: SignInBody) = loginApi.signIn(signInBody)
    suspend fun signIn(signInBody: SignInBody): String? {
        val result = loginApi.signIn(signInBody)
        when (result.type) {
            "result" -> {
                val signInBody = SignInBody(token = result.data.token, _userid = result.data.userid)
                Log.i("user","new token "+result.data.token)
                Log.i("user","new userid"+result.data.userid)

                val data = stringify(signInBody)
                val result2 = getUserById(data!!)
                val userData = result2.data[0]
                val user =
                    User(
                        name = userData.fullname, email = userData.email, token = result.data.token,
                        _userId = result.data.userid
                    )
                saveUserData(user)
                return result2.type
            }
            "error" -> return result.text
        }
        return null
    }


    suspend fun getUserById(signInBody: String) = loginApi.getUserData(signInBody)


    suspend fun resetPassword(signInBody: SignInBody) = loginApi.resetPassword(signInBody)
    suspend fun get(signInBody: SignInBody) = loginApi.getvehicle(signInBody)

}

