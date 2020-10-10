package com.softwareDrivingNetwork.sdn.models.login

import com.squareup.moshi.Json


data class SignInResponseDay(
    @Json(name = "data")
    val data: List<Data>,
    @Json(name = "type")
    val type: String,
    @Json(name = "text")
    val text: String
)