package com.softwareDrivingNetwork.sdn.features.drawer_tabs.history

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import com.softwareDrivingNetwork.sdn.R
import com.softwareDrivingNetwork.sdn.common.BaseActivity
import com.softwareDrivingNetwork.sdn.features.drawer_tabs.vehicles.VehiclesViewModel
import org.koin.android.viewmodel.ext.android.viewModel


class HistoryTracks : BaseActivity() {

    private val vehiclesViewModel: VehiclesViewModel by viewModel()

    override fun provideLayout() = R.layout.history_tracks
    override fun passNameToActivity(): String? =
        resources.getString(R.string.history_tracking_title)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        vehiclesViewModel.getHistoryLocation(getStringifiedDataForHistoryTracking()!!)
        vehiclesViewModel.historyResponse.observe(this, Observer {
            Log.i("history",it.toString())
        })
        vehiclesViewModel.errorResponse.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })


    }
}
