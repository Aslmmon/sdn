package com.softwareDrivingNetwork.sdn.features.home.fragments.live_tracking

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.softwareDrivingNetwork.sdn.R
import com.softwareDrivingNetwork.sdn.common.BaseFragment
import com.softwareDrivingNetwork.sdn.common.Constants
import com.softwareDrivingNetwork.sdn.common.setSafeOnClickListener
import com.softwareDrivingNetwork.sdn.features.drawer_tabs.vehicles.VehiclesViewModel
import com.softwareDrivingNetwork.sdn.features.home.fragments.CameraLocation
import com.softwareDrivingNetwork.sdn.features.home.fragments.SharedViewModel
import com.softwareDrivingNetwork.sdn.features.home.fragments.live_tracking.adapter.CommonAdapter
import com.softwareDrivingNetwork.sdn.features.home.fragments.live_tracking.adapter.GroupsSpinnerAdapter
import com.softwareDrivingNetwork.sdn.features.home.fragments.live_tracking.bottom_sheet.SearchDialogFragment
import com.softwareDrivingNetwork.sdn.models.general.common.CommonModel
import com.softwareDrivingNetwork.sdn.models.general.common.VehiclesData
import com.softwareDrivingNetwork.sdn.models.general.groups.GroupsData
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_camera_vehicle_chooser.*
import org.koin.android.viewmodel.ext.android.viewModel


class liveTracking() : BaseFragment(),
    AdapterView.OnItemSelectedListener {

    lateinit var commonAdapter: CommonAdapter
    lateinit var cameraLocation: CameraLocation
    lateinit var groupsSpinnerAdapter: GroupsSpinnerAdapter
    var CommonList = mutableListOf<CommonModel>()
    private val vehiclesViewModel: VehiclesViewModel by viewModel()
    private val model: SharedViewModel by activityViewModels()

    companion object {
        fun newInstance(remove: Int): liveTracking {
            val args = Bundle()
            args.putInt("remove", remove)
            val fragment = liveTracking()
            fragment.arguments = args
            return fragment
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = this.arguments

        if (bundle != null) {
            val myInt = bundle.getInt("remove", 0)
            if (myInt == 1) btn_submit.visibility = View.GONE
        }

        showToolbar()
        initializeAdapter()
        initializeSpinner()
        vehiclesViewModel.getGroups(getStringifiedData()!!)
        vehiclesViewModel.groupsResponse.observe(viewLifecycleOwner, Observer {
            groupsSpinnerAdapter = GroupsSpinnerAdapter(requireActivity(), it.data)
            spinner_groups.adapter = groupsSpinnerAdapter

        })

        tv_search.setSafeOnClickListener {
            val bottomSheetFragment = SearchDialogFragment()
            bottomSheetFragment.show(
                requireActivity().supportFragmentManager,
                bottomSheetFragment.tag
            )
        }

        model.searchString.observe(viewLifecycleOwner, Observer { searchItem ->
            val searchedList = CommonList.filter { it.name.contains(searchItem) }.distinct()
            commonAdapter.submitList(searchedList)
            commonAdapter.notifyDataSetChanged()
        })


        vehiclesViewModel.camerasResponse.observe(viewLifecycleOwner, Observer {
            val newData = it.data.map { data ->
                CommonModel(
                    data.registerid, data.sensorName, lat = data.lastLocation.lat,
                    long = data.lastLocation.lng, groupName = data.groups[0].groupName
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
                    data.groups[0].groupName,
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

            if (cameraLocation.lat != null) {
                val bundle = bundleOf(Constants.NAVIGATION to Constants.FROM_LIVE_TRACKNG_TAB)
                findNavController().navigate(R.id.goToLiveTrack, bundle)
            } else Toast.makeText(activity, "choose Camera or Vehicle Please", Toast.LENGTH_SHORT)
                .show()
        }


        spinner_groups.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                p3: Long
            ) {
                val itemClicked = parent?.getItemAtPosition(position) as GroupsData
                val newList = CommonList.filter { it -> it.groupName == itemClicked.group_name }
                commonAdapter.submitList(newList)
                commonAdapter.notifyDataSetChanged()

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
        //  showProgress()
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