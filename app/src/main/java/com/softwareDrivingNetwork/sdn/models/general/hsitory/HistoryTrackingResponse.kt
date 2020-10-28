package com.softwareDrivingNetwork.sdn.models.general.hsitory

import com.google.gson.annotations.SerializedName

data class HistoryTrackingResponse(
    val `data`: List<HistoryTrackingData>,
    val type: String,
    @SerializedName("value")
    val value: String,
    @SerializedName("text")
    val text: String
)