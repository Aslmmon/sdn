package com.softwareDrivingNetwork.sdn.core.api

import com.softwareDrivingNetwork.sdn.models.general.cameras.CameraListResponse
import com.softwareDrivingNetwork.sdn.models.general.vehicles.VehiclesListResponse
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface GeneralApis {

    @POST("/api/vehicle.getvehiclelist")
    @FormUrlEncoded
    suspend fun getVehiclesLst(@Field("data") signInBody: String): VehiclesListResponse

    @POST("/api/cam.getcamlist")
    @FormUrlEncoded
    suspend fun getCameraList(@Field ("data") signInBody: String): CameraListResponse

}