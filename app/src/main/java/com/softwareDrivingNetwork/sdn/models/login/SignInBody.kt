package com.softwareDrivingNetwork.sdn.models.login


data class SignInBody(
    var email: String? = null,
    var password: String? = null,
    var raw: Boolean = true,
    var userid: String = "-200",
    var token: String? = null,
    var app_version: Int = 49,
    var _userid: String? = null,
    var job_name:String?="active_location_job",
    var start_time: String ?=null,
    var end_time: String ?=null,
    var playmode:Boolean?=true,
    var objectids:MutableList<String>?=null,
    var min_speed:Int?=null,
    var start:Int?=null,
    var limit:Int?=null
)