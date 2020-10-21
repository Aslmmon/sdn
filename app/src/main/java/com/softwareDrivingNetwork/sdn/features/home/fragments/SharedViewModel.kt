package com.softwareDrivingNetwork.sdn.features.home.fragments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    val selected = MutableLiveData<CameraLocation>()

    fun select(item: CameraLocation) {
        selected.value = item
    }
}

data class CameraLocation(var lat: Double, var long: Double,var id:String)