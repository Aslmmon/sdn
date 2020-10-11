package com.softwareDrivingNetwork.sdn.models.general.drivers

data class driversModel(
    val accountid: String,
    val current_mileage: Int,
    val driver_name: String,
    val driverid: String,
    val driving_score: Int,
    val global_perc_score: Any,
    val id_info: Any,
    val last_loc_update: Any,
    val last_location: Any,
    val last_update: Any,
    val license_end: String,
    val license_info: Any,
    val license_number: String,
    val license_start: String,
    val local_perc_score: Any,
    val plate_no: String,
    val sdn_perc_score: Any,
    val vehicle_name: String,
    val vehicleid: String
)