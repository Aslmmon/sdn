package com.softwareDrivingNetwork.sdn.features.login.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.softwareDrivingNetwork.sdn.common.launchDataLoad
import com.softwareDrivingNetwork.sdn.core.repo.LoginRepo
import com.softwareDrivingNetwork.sdn.models.login.SignInBody
import com.softwareDrivingNetwork.sdn.models.login.SignInResponseDay
import com.softwareDrivingNetwork.sdn.models.login.response.ResetPasswordResponse
import com.softwareDrivingNetwork.sdn.models.login.response.SessionTokenResponse

class LoginViewModel(var loginRepo: LoginRepo) : ViewModel() {

//    private val _signInResponse = MutableLiveData<SessionTokenResponse>()
//    val signInResponse: LiveData<SessionTokenResponse> = _signInResponse

    private val _signInResponse = MutableLiveData<String>()
    val signInResponse: LiveData<String> = _signInResponse


    private val _resetPasswordResponse = MutableLiveData<ResetPasswordResponse>()
    val resetPasswordResponse: LiveData<ResetPasswordResponse> = _resetPasswordResponse

    private val _errorResponse = MutableLiveData<Throwable>()
    val errorResponse: LiveData<Throwable> = _errorResponse


//    private val _userData = MutableLiveData<SignInResponseDay>()
//    val userData: LiveData<SignInResponseDay> = _userData

    fun signIn(user: SignInBody) {
        launchDataLoad(execution = {
            _signInResponse.value = loginRepo.signIn(user)

        }, errorReturned = {
            _errorResponse.value = it
        })

    }

    fun resetPassword(user: SignInBody) {
        launchDataLoad(execution = {
            _resetPasswordResponse.value = loginRepo.resetPassword(user)
        }, errorReturned = {
            _errorResponse.value = it
        })

    }
//
//    fun getUserData(user: String) {
//        launchDataLoad(execution = {
//            _userData.value = loginRepo.getUserById(user)
//        }, errorReturned = {
//            _errorResponse.value = it
//        })
//
//    }


}