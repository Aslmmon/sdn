package com.softwareDrivingNetwork.sdn.models.general.hsitory

data class HistoryTrackingData(
    val accuracy: Double,
    val dev_mileage: Double,
    val extrajson: Any,
    val heading: Double,
    val idle_time:Double,
    val lat: Double,
    val lng: Double,
    val loc_day: String,
    val loc_time: String,
    val objectid: String,
    val sdn_mileage: Double,
    val speed: Double
)