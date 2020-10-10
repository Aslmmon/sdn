package com.softwareDrivingNetwork.sdn.models.login.response


import com.google.gson.annotations.SerializedName

data class SessionTokenResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("type")
    val type: String,
    @SerializedName("value")
    val value: String,
    @SerializedName("text")
    val text: String
)