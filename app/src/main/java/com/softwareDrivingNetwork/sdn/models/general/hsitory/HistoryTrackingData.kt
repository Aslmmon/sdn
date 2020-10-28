package com.softwareDrivingNetwork.sdn.models.general.hsitory

data class HistoryTrackingData(
    val accuracy: Int,
    val dev_mileage: Int,
    val extrajson: Any,
    val heading: Int,
    val idle_time: Int,
    val lat: Double,
    val lng: Double,
    val loc_day: String,
    val loc_time: String,
    val objectid: String,
    val sdn_mileage: Double,
    val speed: Double
)