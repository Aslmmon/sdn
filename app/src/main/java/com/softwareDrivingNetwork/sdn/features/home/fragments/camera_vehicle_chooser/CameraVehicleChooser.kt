package com.softwareDrivingNetwork.sdn.features.home.fragments.camera_vehicle_chooser

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.softwareDrivingNetwork.sdn.R
import com.softwareDrivingNetwork.sdn.common.BaseFragment
import com.softwareDrivingNetwork.sdn.features.drawer_tabs.vehicles.VehiclesViewModel
import com.softwareDrivingNetwork.sdn.features.home.fragments.CameraLocation
import com.softwareDrivingNetwork.sdn.features.home.fragments.SharedViewModel
import com.softwareDrivingNetwork.sdn.features.home.fragments.camera_vehicle_chooser.adapter.CommonAdapter
import com.softwareDrivingNetwork.sdn.models.general.common.CommonModel
import com.softwareDrivingNetwork.sdn.models.general.common.VehiclesData
import kotlinx.android.synthetic.main.fragment_camera_vehicle_chooser.*
import org.koin.android.viewmodel.ext.android.viewModel


class CameraVehicleChooser : BaseFragment(), CommonAdapter.Interaction,
    AdapterView.OnItemSelectedListener {

    lateinit var commonAdapter: CommonAdapter

    private val vehiclesViewModel: VehiclesViewModel by viewModel()
    private val model: SharedViewModel by activityViewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showToolbar()
        initializeAdapter()
        initializeSpinner()
        vehiclesViewModel.camerasResponse.observe(viewLifecycleOwner, Observer {
            val newData = it.data.map { data ->
                CommonModel(
                    data.registerid, data.sensorName, lat = data.lastLocation.lat,
                    long = data.lastLocation.lng
                )
            }
            submitListData(newData)
        })
        vehiclesViewModel.errorResponse.observe(viewLifecycleOwner, Observer {
            dismissProgressDialog()
            Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
        })
        vehiclesViewModel.vehiclesResponse.observe(viewLifecycleOwner, Observer {
            val newData = it.data.map { data ->
                CommonModel(
                    data.gpsUnitid,
                    data.vehicleName,
                    data.locationLat,
                    data.locationLng,
                    VehiclesData(
                        data.currentMileage,
                        data.maxSpeed,
                        data.plateNo,
                        data.simNumber,
                        data.vehicleName
                    )
                )
            }
            submitListData(newData)

        })


        btn_submit.setOnClickListener {
            findNavController().navigate(R.id.goToLiveTrack)
        }


    }

    private fun submitListData(it: List<CommonModel>) {
        dismissProgressDialog()
        commonAdapter.submitList(it)
        commonAdapter.notifyDataSetChanged()
    }


    private fun initializeSpinner() {
        activity?.let {
            ArrayAdapter.createFromResource(
                it,
                R.array.cameras_array,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner_cameras.adapter = adapter
            }
        }
        spinner_cameras.onItemSelectedListener = this


        activity?.let {
            ArrayAdapter.createFromResource(
                it,
                R.array.groups_array,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                // Apply the adapter to the spinner
                spinner_groups.adapter = adapter
            }
        }


    }

    private fun getCameraLists() {
        vehiclesViewModel.getCameraList(getStringifiedData()!!)
    }

    private fun getVehicles() {
        vehiclesViewModel.getVehiclersList(getStringifiedData()!!)
    }

    private fun initializeAdapter() {
        commonAdapter = CommonAdapter(this)
        rv_cameras_list.apply {
            adapter = commonAdapter
        }

    }

    override fun provideLayout() = R.layout.fragment_camera_vehicle_chooser


    override fun onNothingSelected(parent: AdapterView<*>?) {
        dismissProgressDialog()
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when (position) {
            0 -> getCameraLists()
            1 -> getVehicles()
        }
        showProgress()
    }

    override fun onItemSelected(position: Int, item: CommonModel) {
        val cameraLocation = CameraLocation(long = item.lat, lat = item.long, id = item.id)
        Toast.makeText(activity, cameraLocation.lat.toString(), Toast.LENGTH_SHORT).show()
        model.select(cameraLocation)
    }

}