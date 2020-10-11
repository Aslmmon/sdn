package com.softwareDrivingNetwork.sdn.models.general.drivers

import com.google.gson.annotations.SerializedName

data class DriversListReponse(
    val `data`: List<driversModel>,
    val type: String,
    @SerializedName("value")
    val value: String,
    @SerializedName("text")
    val text: String
)