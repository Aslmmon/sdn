package com.softwareDrivingNetwork.sdn.models.general.vehicles


import com.google.gson.annotations.SerializedName

data class Group(
    @SerializedName("accountid")
    val accountid: String,
    @SerializedName("group_name")
    val groupName: String,
    @SerializedName("groupid")
    val groupid: String,
    @SerializedName("icon_url")
    val iconUrl: Any,
    @SerializedName("unitid")
    val unitid: String
)