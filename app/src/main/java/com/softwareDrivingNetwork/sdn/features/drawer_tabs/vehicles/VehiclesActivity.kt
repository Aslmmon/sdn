package com.softwareDrivingNetwork.sdn.features.drawer_tabs.vehicles;

import android.os.Bundle
import android.widget.Toast
import com.softwareDrivingNetwork.sdn.R
import com.softwareDrivingNetwork.sdn.common.BaseActivity
import com.softwareDrivingNetwork.sdn.features.drawer_tabs.cut_of_engine_vehicles.VehiclesViewModel
import com.softwareDrivingNetwork.sdn.features.drawer_tabs.vehicles.adapter.VehiclesAdapter
import com.softwareDrivingNetwork.sdn.models.general.vehicles.VehiclerModel
import kotlinx.android.synthetic.main.activity_vehicles2.*
import org.koin.android.viewmodel.ext.android.viewModel

class VehiclesActivity : BaseActivity(), VehiclesAdapter.Interaction {
    private val vehiclesViewModel: VehiclesViewModel by viewModel()
    lateinit var vehiclesAdapter: VehiclesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()

        getVehiclesList()

        vehiclesViewModel.vehiclesResponse.observe(this,  {
            dismissProgressDialog()
            if (it.type == "error") {
                Toast.makeText(this, it.text, Toast.LENGTH_SHORT).show()
            } else {
                vehiclesAdapter.submitList(it.data)
            }


        })

    }


    private fun getVehiclesList() {
        showProgress()
        vehiclesViewModel.getVehiclersList(getStringifiedData()!!)
    }
    private fun initialize() {
        vehiclesAdapter = VehiclesAdapter(this)
        rv_vehicles.apply {
            adapter = vehiclesAdapter
        }

    }


    override fun provideLayout(): Int = R.layout.activity_vehicles2


    override fun passNameToActivity(): String? = resources.getString(R.string.vehicles_title)
    override fun onItemSelected(position: Int, item: VehiclerModel, isChecked: Boolean) {
    }
}