package com.softwareDrivingNetwork.sdn.models.general.cameras


import com.google.gson.annotations.SerializedName
import com.softwareDrivingNetwork.sdn.models.general.vehicles.Group

data class CameraModel(
    @SerializedName("accountid")
    val accountid: String,
    @SerializedName("ai_type")
    val aiType: String,
    @SerializedName("current_status")
    val currentStatus: String,
    @SerializedName("driver")
    val driver: Driver,
    @SerializedName("groups")
    val groups: List<Group>,
    @SerializedName("install_date")
    val installDate: String,
    @SerializedName("last_command_date")
    val lastCommandDate: String,
    @SerializedName("last_loc_update")
    val lastLocUpdate: String,
    @SerializedName("last_location")
    val lastLocation: LastLocation,
    @SerializedName("last_update_date")
    val lastUpdateDate: Any,
    @SerializedName("location_lat")
    val locationLat: Double?=null,
    @SerializedName("location_lng")
    val locationLng: Double?=null,
    @SerializedName("location_url")
    val locationUrl: String,
    @SerializedName("plate_no")
    val plateNo: String,
    @SerializedName("registerid")
    val registerid: String,
    @SerializedName("sensor_name")
    val sensorName: String,
    @SerializedName("sensorid")
    val sensorid: String,
    @SerializedName("silent_mode")
    val silentMode: Boolean,
    @SerializedName("terminal_ver")
    val terminalVer: String,
    @SerializedName("vehicle")
    val vehicle: Vehicle,
    @SerializedName("vehicle_name")
    val vehicleName: String,
    @SerializedName("vehicleid")
    val vehicleid: Any
)