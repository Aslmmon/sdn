package com.softwareDrivingNetwork.sdn.models.login.response


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("token")
    val token: String,
    @SerializedName("userid")
    val userid: String
)