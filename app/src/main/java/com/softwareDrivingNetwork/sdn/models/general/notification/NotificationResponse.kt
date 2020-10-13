package com.softwareDrivingNetwork.sdn.models.general.notification

import com.google.gson.annotations.SerializedName

data class NotificationResponse(
    val `data`: List<NotificationModel>,
    val type: String,
    @SerializedName("value")
    val value: String,
    @SerializedName("text")
    val text: String
)