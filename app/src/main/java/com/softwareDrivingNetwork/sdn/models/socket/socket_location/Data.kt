package com.softwareDrivingNetwork.sdn.models.socket.socket_location

data class Data(
    val accountId: String,
    val accuracy: Int,
    val dev_mileage: Int,
    val heading: Int,
    val lat: Double,
    val lng: Double,
    val locTime: String,
    val loc_type: String,
    val objectId: String,
    val objectType: String,
    val sdn_mileage: String,
    val speed: Int,
    val tfmeLocation: TfmeLocation,
    val userId: String,
    val vehicle: Vehicle
)