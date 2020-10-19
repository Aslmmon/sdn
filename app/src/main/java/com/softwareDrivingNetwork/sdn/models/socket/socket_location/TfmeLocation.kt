package com.softwareDrivingNetwork.sdn.models.socket.socket_location

import com.softwareDrivingNetwork.sdn.models.socket.socket_location.Tem

data class TfmeLocation(
    val ac: Int,
    val alt: Int,
    val bat: String,
    val drc: Int,
    val dt: String,
    val evt: String,
    val gas: Int,
    val ip: String,
    val lat: Double,
    val lng: Double,
    val loc_type: String,
    val ma: Int,
    val pwd: String,
    val spd: Int,
    val tcp_port: String,
    val tem: Tem,
    val uid: String,
    val usr: String
)