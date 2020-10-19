package com.softwareDrivingNetwork.sdn.models.socket.socket_location

data class Vehicle(
    val accountid: String,
    val current_driverid: Any,
    val current_mileage: Int,
    val gps_unitid: String,
    val last_loc_update: String,
    val last_location: String,
    val license_end: String,
    val license_start: String,
    val max_speed: Int,
    val plate_no: String,
    val sim_number: Any,
    val vehicle_name: String,
    val vehicleid: String
)