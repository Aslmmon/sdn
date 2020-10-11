package com.softwareDrivingNetwork.sdn.models.general.cameras


import com.google.gson.annotations.SerializedName

data class CameraListResponse(
    @SerializedName("data")
    val `data`: List<CameraModel>,
    @SerializedName("type")
    val type: String,
    @SerializedName("value")
    val value: String,
    @SerializedName("text")
    val text: String

)