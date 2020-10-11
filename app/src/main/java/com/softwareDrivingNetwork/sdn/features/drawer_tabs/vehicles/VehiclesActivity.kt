package com.softwareDrivingNetwork.sdn.features.drawer_tabs.vehicles

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.softwareDrivingNetwork.sdn.R
import com.softwareDrivingNetwork.sdn.common.BaseActivity
import com.softwareDrivingNetwork.sdn.features.drawer_tabs.vehicles.adapter.VehiclesAdapter
import kotlinx.android.synthetic.main.activity_vehicles.*
import org.koin.android.viewmodel.ext.android.viewModel

class VehiclesActivity : BaseActivity() {
    private val vehiclesViewModel: VehiclesViewModel by viewModel()
    lateinit var vehiclesAdapter: VehiclesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.title = resources.getString(R.string.vehicles_title)
        showBackButton()
        initialize()
        getVehiclesList()

        vehiclesViewModel.vehiclesResponse.observe(this, Observer {
            if(it.type == "error"){
                Toast.makeText(this, it.text, Toast.LENGTH_SHORT).show()
                dismissProgressDialog()
            }else{
                vehiclesAdapter.submitList(it.data)
                dismissProgressDialog()
            }


        })

    }

    private fun initialize() {
        vehiclesAdapter = VehiclesAdapter()
        rv_vehicles.apply {
            layoutManager = LinearLayoutManager(this@VehiclesActivity)
            adapter = vehiclesAdapter
        }

    }

    private fun getVehiclesList() {
        showProgress()
        vehiclesViewModel.getVehiclersList(getStringifiedData()!!)
    }

    override fun provideLayout() = R.layout.activity_vehicles
}