package com.softwareDrivingNetwork.sdn.features.drawer_tabs.vehicles

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.softwareDrivingNetwork.sdn.common.launchDataLoad
import com.softwareDrivingNetwork.sdn.core.repo.GeneralRepo
import com.softwareDrivingNetwork.sdn.core.repo.LoginRepo
import com.softwareDrivingNetwork.sdn.models.general.cameras.CameraListResponse
import com.softwareDrivingNetwork.sdn.models.general.drivers.DriversListReponse
import com.softwareDrivingNetwork.sdn.models.general.groups.GroupsResponse
import com.softwareDrivingNetwork.sdn.models.general.hsitory.HistoryTrackingResponse
import com.softwareDrivingNetwork.sdn.models.general.notification.NotificationResponse
import com.softwareDrivingNetwork.sdn.models.general.vehicles.VehiclesListResponse
import com.softwareDrivingNetwork.sdn.models.login.SignInBody
import com.softwareDrivingNetwork.sdn.models.login.SignInResponseDay

class VehiclesViewModel(var generalRepo: GeneralRepo, var loginRepo: LoginRepo) : ViewModel() {


    private val _errorResponse = MutableLiveData<String>()
    val errorResponse: LiveData<String> = _errorResponse


    private val _vehiclesResponse = MutableLiveData<VehiclesListResponse>()
    val vehiclesResponse: LiveData<VehiclesListResponse> = _vehiclesResponse

    private val _camerasResponse = MutableLiveData<CameraListResponse>()
    val camerasResponse: LiveData<CameraListResponse> = _camerasResponse

    private val _groupsResponse = MutableLiveData<GroupsResponse>()
    val groupsResponse: LiveData<GroupsResponse> = _groupsResponse


    private val _driversResponse = MutableLiveData<DriversListReponse>()
    val driversResponse: LiveData<DriversListReponse> = _driversResponse


    private val _notificationResponse = MutableLiveData<NotificationResponse>()
    val notificationResponse: LiveData<NotificationResponse> = _notificationResponse

    private val _historyResponse = MutableLiveData<HistoryTrackingResponse>()
    val historyResponse: LiveData<HistoryTrackingResponse> = _historyResponse

    fun getVehiclersList(user: String) {
        launchDataLoad(execution = {
            val result = generalRepo.getVehiclesList(user)
            if (result.type != "error") {
                _vehiclesResponse.value = result
            } else _errorResponse.value = result.text
        }, errorReturned = {
            _errorResponse.value = it.message
        })

    }

    fun getCameraList(user: String) {
        launchDataLoad(execution = {
            val result = generalRepo.getCameraList(user)
            if (result.type != "error") {
                _camerasResponse.value = result
            } else _errorResponse.value = result.text
        }, errorReturned = {
            _errorResponse.value = it.message
        })
    }

    fun getGroups(user: String) {
        launchDataLoad(execution = {
            val result = generalRepo.getGroups(user)
            if (result.type != "error") {
                _groupsResponse.value = result
            } else _errorResponse.value = result.text
        }, errorReturned = {
            _errorResponse.value = it.message
        })
    }

    fun getDriversList(user: String) {
        launchDataLoad(execution = {
            val result = generalRepo.getDrivers(user)
            if (result.type != "error") {
                _driversResponse.value = result
            } else _errorResponse.value = result.value
        }, errorReturned = {
            _errorResponse.value = it.message
        })
    }

    fun getNotification(user: String) {
        launchDataLoad(execution = {
            val result = generalRepo.getNotification(user)
            if (result.type != "error") {
                _notificationResponse.value = result
            } else _errorResponse.value = result.value
        }, errorReturned = {
            _errorResponse.value = it.message
        })
    }

    fun getHistoryLocation(user: String) {
        launchDataLoad(execution = {
            val result = generalRepo.getHistoryLocations(user)
            if (result.type != "error") {
                _historyResponse.value = result
            } else _errorResponse.value = result.value
        }, errorReturned = {
            _errorResponse.value = it.message
        })
    }
}