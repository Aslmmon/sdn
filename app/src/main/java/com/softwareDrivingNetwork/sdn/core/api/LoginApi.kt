package com.softwareDrivingNetwork.sdn.core.api

import com.softwareDrivingNetwork.sdn.models.login.SignInBody
import com.softwareDrivingNetwork.sdn.models.login.SignInResponseDay
import com.softwareDrivingNetwork.sdn.models.login.response.ResetPasswordResponse
import com.softwareDrivingNetwork.sdn.models.login.response.SessionTokenResponse
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface LoginApi {
    @POST("/api/user.getsessiontoken")
    suspend fun signIn(@Body signInBody: SignInBody): SessionTokenResponse

    @POST("/api/user.getuserbyid")
    @FormUrlEncoded
    suspend fun getUserData(@Field("data") signInBody: String): SignInResponseDay

    @POST("/api/sendresetpassword")
    suspend fun resetPassword(@Body signInBody: SignInBody): ResetPasswordResponse


    @POST("/api/vehicle.getvehiclelist")
    @FormUrlEncoded
    suspend fun getvehicle(@Field("data") signInBody: SignInBody): SessionTokenResponse
}