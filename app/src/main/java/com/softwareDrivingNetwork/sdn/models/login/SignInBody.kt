package com.softwareDrivingNetwork.sdn.models.login


data class SignInBody(
    var email: String? = null,
    var password: String? = null,
    var raw: Boolean = true,
    var userid: String = "-200",
    var token: String ?=null,
    var app_version: Int = 49,
    var _userid: String ?=null
)