package com.softwareDrivingNetwork.sdn.features.home.fragments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    val selected = MutableLiveData<CameraLocation>()

    val searchString = MutableLiveData<String>()


    fun select(item: CameraLocation) {
        selected.value = item
    }

    fun search(item:String) {
        searchString.value = item
    }
}

data class CameraLocation(var lat: Double?=null, var long: Double?=null,var id:String?=null)