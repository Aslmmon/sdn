package com.softwareDrivingNetwork.sdn.core.repo

import com.softwareDrivingNetwork.sdn.core.api.GeneralApis
import com.softwareDrivingNetwork.sdn.core.api.LoginApi
import com.softwareDrivingNetwork.sdn.models.login.SignInBody

class GeneralRepo(var generalApis:GeneralApis) {

    suspend fun getVehiclesList(signInBody: String) = generalApis.getVehiclesLst(signInBody)

    suspend fun getCameraList(signInBody: String) = generalApis.getCameraList(signInBody)

    suspend fun getDrivers(signInBody: String) = generalApis.getDriversList(signInBody)

    suspend fun getNotification(signInBody: String) = generalApis.getNotification(signInBody)


}

