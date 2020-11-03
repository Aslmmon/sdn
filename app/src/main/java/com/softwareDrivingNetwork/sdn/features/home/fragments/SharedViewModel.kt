package com.softwareDrivingNetwork.sdn.features.home.fragments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    val selected = MutableLiveData<CameraLocation>()
    val timeListener = MutableLiveData<TimeStart>()

    val searchString = MutableLiveData<String>()


    fun select(item: CameraLocation) {
        selected.value = item
    }

    fun shareTime(item:TimeStart){
        timeListener.value = item
    }

    fun search(item:String) {
        searchString.value = item
    }
}

data class CameraLocation(var lat: Double?=null, var long: Double?=null,var id:String?=null)
data class TimeStart(
    var startTime: String? = null,
    var endTime: String? = null,
    var playSpeed: Int? = null,
    val minimumSpeed: Int? =null
)