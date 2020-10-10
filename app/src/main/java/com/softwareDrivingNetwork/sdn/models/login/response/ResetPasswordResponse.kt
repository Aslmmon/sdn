package com.softwareDrivingNetwork.sdn.models.login.response


import com.google.gson.annotations.SerializedName

data class ResetPasswordResponse(
    @SerializedName("text")
    val text: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("value")
    val value: String
)