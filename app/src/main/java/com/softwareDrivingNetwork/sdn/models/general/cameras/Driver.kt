package com.softwareDrivingNetwork.sdn.models.general.cameras


import com.google.gson.annotations.SerializedName

data class Driver(
    @SerializedName("accountid")
    val accountid: String,
    @SerializedName("current_mileage")
    val currentMileage: Int,
    @SerializedName("driver_name")
    val driverName: String,
    @SerializedName("driverid")
    val driverid: String,
    @SerializedName("driving_score")
    val drivingScore: Int,
    @SerializedName("id_info")
    val idInfo: Any,
    @SerializedName("last_loc_update")
    val lastLocUpdate: Any,
    @SerializedName("last_location")
    val lastLocation: Any,
    @SerializedName("last_update")
    val lastUpdate: Any,
    @SerializedName("license_end")
    val licenseEnd: String,
    @SerializedName("license_info")
    val licenseInfo: Any,
    @SerializedName("license_number")
    val licenseNumber: String,
    @SerializedName("license_start")
    val licenseStart: String,
    @SerializedName("vehicleid")
    val vehicleid: String
)