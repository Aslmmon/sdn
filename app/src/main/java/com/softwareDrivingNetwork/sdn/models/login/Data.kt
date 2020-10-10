package com.softwareDrivingNetwork.sdn.models.login


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("about")
    val about: Any,
    @SerializedName("accountid")
    val accountid: String,
    @SerializedName("birthdate")
    val birthdate: Any,
    @SerializedName("clientid")
    val clientid: Any,
    @SerializedName("countryid")
    val countryid: String,
    @SerializedName("current_rate")
    val currentRate: Any,
    @SerializedName("email")
    val email: String,
    @SerializedName("email_ok")
    val emailOk: Boolean,
    @SerializedName("fullname")
    val fullname: String,
    @SerializedName("gender")
    val gender: Any,
    @SerializedName("job")
    val job: Any,
    @SerializedName("last_sms_code")
    val lastSmsCode: Any,
    @SerializedName("lastlocation")
    val lastlocation: Any,
    @SerializedName("lastupdate")
    val lastupdate: String,
    @SerializedName("lat")
    val lat: Any,
    @SerializedName("likes")
    val likes: Any,
    @SerializedName("location")
    val location: Any,
    @SerializedName("long")
    val long: Any,
    @SerializedName("mobile_ok")
    val mobileOk: Boolean,
    @SerializedName("mobileno")
    val mobileno: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("profilephotourl")
    val profilephotourl: Any,
    @SerializedName("reset_password_expiresin")
    val resetPasswordExpiresin: Any,
    @SerializedName("reset_password_token")
    val resetPasswordToken: Any,
    @SerializedName("roles")
    val roles: List<String>,
    @SerializedName("sms_code")
    val smsCode: Any,
    @SerializedName("studyat")
    val studyat: Any,
    @SerializedName("timezone")
    val timezone: String,
    @SerializedName("userCategory")
    val userCategory: String,
    @SerializedName("userid")
    val userid: String,
    @SerializedName("valid_access_token")
    val validAccessToken: Boolean,
    @SerializedName("wallet_access_token")
    val walletAccessToken: String,
    @SerializedName("wallet_token")
    val walletToken: Any,
    @SerializedName("workat")
    val workat: Any,
    @SerializedName("youtube")
    val youtube: Any
)