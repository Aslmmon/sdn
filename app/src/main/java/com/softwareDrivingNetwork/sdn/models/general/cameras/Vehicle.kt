package com.softwareDrivingNetwork.sdn.models.general.cameras


import com.google.gson.annotations.SerializedName

data class Vehicle(
    @SerializedName("accountid")
    val accountid: String,
    @SerializedName("current_driverid")
    val currentDriverid: Any,
    @SerializedName("current_mileage")
    val currentMileage: Int,
    @SerializedName("gps_unitid")
    val gpsUnitid: String,
    @SerializedName("last_loc_update")
    val lastLocUpdate: Any,
    @SerializedName("last_location")
    val lastLocation: Any,
    @SerializedName("license_end")
    val licenseEnd: String,
    @SerializedName("license_start")
    val licenseStart: String,
    @SerializedName("location_lat")
    val locationLat: Any,
    @SerializedName("location_lng")
    val locationLng: Any,
    @SerializedName("max_speed")
    val maxSpeed: Any,
    @SerializedName("plate_no")
    val plateNo: String,
    @SerializedName("sim_number")
    val simNumber: Any,
    @SerializedName("vehicle_name")
    val vehicleName: String,
    @SerializedName("vehicleid")
    val vehicleid: String
)