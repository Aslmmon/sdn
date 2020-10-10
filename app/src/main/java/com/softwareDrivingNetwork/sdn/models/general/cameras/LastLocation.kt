package com.softwareDrivingNetwork.sdn.models.general.cameras


import com.google.gson.annotations.SerializedName

data class LastLocation(
    @SerializedName("accuracy")
    val accuracy: Double,
    @SerializedName("dev_mileage")
    val devMileage: Any,
    @SerializedName("extrajson")
    val extrajson: Any,
    @SerializedName("heading")
    val heading: Int,
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("lng")
    val lng: Double,
    @SerializedName("loc_time")
    val locTime: String,
    @SerializedName("objectid")
    val objectid: String,
    @SerializedName("sdn_mileage")
    val sdnMileage: Any,
    @SerializedName("speed")
    val speed: Int
)