package com.softwareDrivingNetwork.sdn.features.home.fragments.camera_vehicle_chooser

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.softwareDrivingNetwork.sdn.R
import com.softwareDrivingNetwork.sdn.common.BaseFragment
import com.softwareDrivingNetwork.sdn.features.drawer_tabs.cameras.adapter.CameraAdapter
import com.softwareDrivingNetwork.sdn.features.drawer_tabs.vehicles.VehiclesViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.fragment_camera_vehicle_chooser.*
import org.koin.android.viewmodel.ext.android.viewModel


class CameraVehicleChooser : BaseFragment() {

    lateinit var camerasAdapter: CameraAdapter
    private val vehiclesViewModel: VehiclesViewModel by viewModel()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeAdapter()
        activity?.toolbar?.visibility = View.GONE
        vehiclesViewModel.getCameraList(getStringifiedData()!!)
        vehiclesViewModel.camerasResponse.observe(viewLifecycleOwner, Observer {
            camerasAdapter.submitList(it.data)
        })
    }

    private fun initializeAdapter() {
        camerasAdapter = CameraAdapter(flag = 1)
        rv_cameras_list.apply {
            adapter = camerasAdapter

        }
    }

    override fun provideLayout() = R.layout.fragment_camera_vehicle_chooser

}