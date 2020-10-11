package com.softwareDrivingNetwork.sdn.features.drawer_tabs.drivers

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.softwareDrivingNetwork.sdn.R
import com.softwareDrivingNetwork.sdn.common.BaseActivity
import com.softwareDrivingNetwork.sdn.features.drawer_tabs.drivers.adapter.DriverAdapter
import com.softwareDrivingNetwork.sdn.features.drawer_tabs.vehicles.VehiclesViewModel
import com.softwareDrivingNetwork.sdn.features.drawer_tabs.vehicles.adapter.VehiclesAdapter
import kotlinx.android.synthetic.main.activity_drivers.*
import kotlinx.android.synthetic.main.activity_vehicles.*
import org.koin.android.viewmodel.ext.android.viewModel

class DriversActivity : BaseActivity() {
    private val vehiclesViewModel: VehiclesViewModel by viewModel()
    lateinit var driverAdapter: DriverAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()

        showProgress()
        vehiclesViewModel.getDriversList(getStringifiedData()!!)
        vehiclesViewModel.driversResponse.observe(this, Observer {
            dismissProgressDialog()
            driverAdapter.submitList(it.data)
        })
        vehiclesViewModel.errorResponse.observe(this, Observer {
            dismissProgressDialog()
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })

    }

    private fun initialize() {
        driverAdapter = DriverAdapter()
        rv_drivers.apply {
            layoutManager = GridLayoutManager(this@DriversActivity, 2)
            adapter = driverAdapter
        }

    }

    override fun provideLayout() = R.layout.activity_drivers

    override fun passNameToActivity(): String? = resources.getString(R.string.drivers_title)

}