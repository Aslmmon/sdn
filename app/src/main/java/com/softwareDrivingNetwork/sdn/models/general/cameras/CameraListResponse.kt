package com.softwareDrivingNetwork.sdn.models.general.cameras


import com.google.gson.annotations.SerializedName

data class CameraListResponse(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("type")
    val type: String
)