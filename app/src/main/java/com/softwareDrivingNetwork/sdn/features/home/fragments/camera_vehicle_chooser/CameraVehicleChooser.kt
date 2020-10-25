package com.softwareDrivingNetwork.sdn.features.home.fragments.camera_vehicle_chooser

import android.os.Bundle
import android.util.Log
import android.view.View
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
import com.softwareDrivingNetwork.sdn.features.home.fragments.camera_vehicle_chooser.adapter.GroupsSpinnerAdapter
import com.softwareDrivingNetwork.sdn.models.general.common.CommonModel
import com.softwareDrivingNetwork.sdn.models.general.common.VehiclesData
import com.softwareDrivingNetwork.sdn.models.general.groups.GroupsData
import kotlinx.android.synthetic.main.fragment_camera_vehicle_chooser.*
import org.koin.android.viewmodel.ext.android.viewModel

class CameraVehicleChooser : BaseFragment(),
    AdapterView.OnItemSelectedListener {

    lateinit var commonAdapter: CommonAdapter
    lateinit var cameraLocation: CameraLocation
    lateinit var groupsSpinnerAdapter: GroupsSpinnerAdapter
    var CommonList = mutableListOf<CommonModel>()
    private val vehiclesViewModel: VehiclesViewModel by viewModel()
    private val model: SharedViewModel by activityViewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showToolbar()
        initializeAdapter()
        initializeSpinner()
        vehiclesViewModel.getGroups(getStringifiedData()!!)
        vehiclesViewModel.groupsResponse.observe(viewLifecycleOwner, Observer {
            groupsSpinnerAdapter = GroupsSpinnerAdapter(requireActivity(), it.data)
            spinner_groups.adapter = groupsSpinnerAdapter

        })


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
            if (it == "no data found") submitListData(mutableListOf())
            dismissProgressDialog()
            //  Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
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
            if (cameraLocation.lat != null) findNavController().navigate(R.id.goToLiveTrack)
            else Toast.makeText(activity, "choose Camera or Vehicle Please", Toast.LENGTH_SHORT)
                .show()
        }


        spinner_groups.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, p3: Long) {
                val itemClicked = parent?.getItemAtPosition(position) as GroupsData
                //CommonList.filter { it -> it.vehicleData == itemClicked.group_name }
            }

        }


    }

    override fun onResume() {
        super.onResume()
        cameraLocation = CameraLocation()
    }

    private fun submitListData(it: List<CommonModel>) {
        CommonList.addAll(it)
        if (it.isEmpty()) showEmptyLayout()
        else {
            dismissEmptyLayout()
            dismissProgressDialog()
            commonAdapter.submitList(CommonList)
        }
        commonAdapter.notifyDataSetChanged()
    }

    private fun dismissEmptyLayout() {
        group_empty.visibility = View.GONE
        rv_cameras_list.visibility = View.VISIBLE
    }

    private fun showEmptyLayout() {
        group_empty.visibility = View.VISIBLE
        rv_cameras_list.visibility = View.GONE
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


    }

    private fun getCameraLists() {
        showProgress()
        Log.i("data", "camera")
        vehiclesViewModel.getCameraList(getStringifiedData()!!)
    }

    private fun getVehicles() {
        Log.i("data", "vehicle")
        vehiclesViewModel.getVehiclersList(getStringifiedData()!!)
    }

    private fun initializeAdapter() {
        commonAdapter = CommonAdapter(clickListener = { item ->
            cameraLocation = CameraLocation(long = item.long, lat = item.lat, id = item.id)
            Toast.makeText(activity, cameraLocation.lat.toString(), Toast.LENGTH_SHORT).show()
            model.select(cameraLocation)
        })
        rv_cameras_list.apply {
            adapter = commonAdapter
        }

    }

    override fun provideLayout() = R.layout.fragment_camera_vehicle_chooser


    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when (position) {
            0 -> getCameraLists()
            1 -> getVehicles()
        }
    }


}