package com.softwareDrivingNetwork.sdn.models.general.common

data class CommonModel(
    var id: String?=null,
    var name: String?=null,
    var lat: Double?=null,
    var long: Double?=null,
    var groupName:String?=null,
    var vehicleData: VehiclesData?=null
)

data class VehiclesData(
    val currentMileage: Int?=null,
    val maxSpeed: Int?=null,
    val plateNo: String?=null,
    val simNumber: Any?=null,
    val vehicleName: String?=null
//    val licenseEnd: String,
//    val licenseInfo: String,
//    val licenseNumber: String,
//    val licenseStart: String,
//    val locationLat: Double,
)