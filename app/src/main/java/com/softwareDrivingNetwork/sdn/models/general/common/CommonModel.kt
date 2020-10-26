package com.softwareDrivingNetwork.sdn.models.general.common

data class CommonModel(
    var id: String,
    var name: String,
    var lat: Double,
    var long: Double,
    var groupName:String,
    var vehicleData: VehiclesData?=null
)

data class VehiclesData(
    val currentMileage: Int?=null,
    val maxSpeed: Any?=null,
    val plateNo: String?=null,
    val simNumber: Any?=null,
    val vehicleName: String?=null
//    val licenseEnd: String,
//    val licenseInfo: String,
//    val licenseNumber: String,
//    val licenseStart: String,
//    val locationLat: Double,
)