package com.softwareDrivingNetwork.sdn.core.api

import com.softwareDrivingNetwork.sdn.models.general.cameras.CameraListResponse
import com.softwareDrivingNetwork.sdn.models.general.drivers.DriversListReponse
import com.softwareDrivingNetwork.sdn.models.general.groups.GroupsResponse
import com.softwareDrivingNetwork.sdn.models.general.hsitory.HistoryTrackingResponse
import com.softwareDrivingNetwork.sdn.models.general.notification.NotificationResponse
import com.softwareDrivingNetwork.sdn.models.general.vehicles.VehiclesListResponse
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


    @POST("/api/groups.accountGroups")
    @FormUrlEncoded
    suspend fun getGroups(@Field("data") signInBody: String): GroupsResponse


    @POST("api/driver.getdriverlist")
    @FormUrlEncoded
    suspend fun getDriversList(@Field("data") signInBody: String): DriversListReponse

    @POST("api/notifications.list")
    @FormUrlEncoded
    suspend fun getNotification(@Field("data") signInBody: String): NotificationResponse

    @POST("api/location.getlocation")
    @FormUrlEncoded
    suspend fun getLocation(@Field("data") signInBody: String): HistoryTrackingResponse

}