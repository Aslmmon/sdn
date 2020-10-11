package com.softwareDrivingNetwork.sdn.features.drawer_tabs.vehicles

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.softwareDrivingNetwork.sdn.common.launchDataLoad
import com.softwareDrivingNetwork.sdn.core.repo.GeneralRepo
import com.softwareDrivingNetwork.sdn.core.repo.LoginRepo
import com.softwareDrivingNetwork.sdn.models.general.cameras.CameraListResponse
import com.softwareDrivingNetwork.sdn.models.general.drivers.DriversListReponse
import com.softwareDrivingNetwork.sdn.models.general.vehicles.VehiclesListResponse
import com.softwareDrivingNetwork.sdn.models.login.SignInBody
import com.softwareDrivingNetwork.sdn.models.login.SignInResponseDay

class VehiclesViewModel(var generalRepo: GeneralRepo) : ViewModel() {


    private val _errorResponse = MutableLiveData<String>()
    val errorResponse: LiveData<String> = _errorResponse


    private val _vehiclesResponse = MutableLiveData<VehiclesListResponse>()
    val vehiclesResponse: LiveData<VehiclesListResponse> = _vehiclesResponse

    private val _camerasResponse = MutableLiveData<CameraListResponse>()
    val camerasResponse: LiveData<CameraListResponse> = _camerasResponse

    private val _driversResponse = MutableLiveData<DriversListReponse>()
    val driversResponse: LiveData<DriversListReponse> = _driversResponse


    fun getVehiclersList(user: String) {
        launchDataLoad(execution = {
            _vehiclesResponse.value = generalRepo.getVehiclesList(user)
        }, errorReturned = {
            _errorResponse.value = it.message
        })

    }

    fun getCameraList(user: String) {
        launchDataLoad(execution = {
            val result = generalRepo.getCameraList(user)
            if (result.type != "error") {
                _camerasResponse.value = result
            } else _errorResponse.value = result.value
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
}