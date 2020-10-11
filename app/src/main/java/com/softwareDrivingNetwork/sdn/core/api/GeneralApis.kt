package com.softwareDrivingNetwork.sdn.core.api

import android.graphics.Camera
import com.softwareDrivingNetwork.sdn.models.general.cameras.CameraListResponse
import com.softwareDrivingNetwork.sdn.models.general.drivers.DriversListReponse
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
    suspend fun getCameraList(@Field("data") signInBody: String): CameraListResponse

    @POST("api/driver.getdriverlist")
    @FormUrlEncoded
    suspend fun getDriversList(@Field("data") signInBody: String): DriversListReponse

}