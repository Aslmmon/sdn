package com.softwareDrivingNetwork.sdn.features.drawer_tabs.vehicles

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.softwareDrivingNetwork.sdn.common.launchDataLoad
import com.softwareDrivingNetwork.sdn.core.repo.GeneralRepo
import com.softwareDrivingNetwork.sdn.core.repo.LoginRepo
import com.softwareDrivingNetwork.sdn.models.general.vehicles.VehiclesListResponse
import com.softwareDrivingNetwork.sdn.models.login.SignInBody
import com.softwareDrivingNetwork.sdn.models.login.SignInResponseDay

class VehiclesViewModel(var generalRepo: GeneralRepo) : ViewModel() {


    private val _errorResponse = MutableLiveData<Throwable>()
    val errorResponse: LiveData<Throwable> = _errorResponse


    private val _vehiclesResponse = MutableLiveData<VehiclesListResponse>()
    val vehiclesResponse: LiveData<VehiclesListResponse> = _vehiclesResponse

    fun getVehiclersList(user: String) {
        launchDataLoad(execution = {
            _vehiclesResponse.value = generalRepo.getVehiclesList(user)
        }, errorReturned = {
            _errorResponse.value = it
        })

    }
}