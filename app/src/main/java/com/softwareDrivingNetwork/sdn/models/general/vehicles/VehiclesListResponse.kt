package com.softwareDrivingNetwork.sdn.models.general.vehicles


import com.google.gson.annotations.SerializedName

data class VehiclesListResponse(
    @SerializedName("data")
    val `data`: List<VehiclerModel>,
    @SerializedName("type")
    val type: String,
    @SerializedName("value")
    val value: String,
    @SerializedName("text")
    val text: String
)