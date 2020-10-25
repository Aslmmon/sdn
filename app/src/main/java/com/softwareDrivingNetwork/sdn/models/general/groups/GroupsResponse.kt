package com.softwareDrivingNetwork.sdn.models.general.groups

import com.google.gson.annotations.SerializedName

data class GroupsResponse(
    val `data`: List<GroupsData>,
    val type: String,
    @SerializedName("value")
    val value: String,
    @SerializedName("text")
    val text: String
)