package com.softwareDrivingNetwork.sdn.features.home.fragments.camera_vehicle_chooser

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
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
        showToolbar()
    //    hideBackButtonInToolbar()
        initializeAdapter()
        initializeSpinner()
        vehiclesViewModel.getCameraList(getStringifiedData()!!)
        vehiclesViewModel.camerasResponse.observe(viewLifecycleOwner, Observer {
            camerasAdapter.submitList(it.data)
        })
        btn_submit.setOnClickListener {
            findNavController().navigate(R.id.goToLiveTrack)
        }


    }


    private fun applyNewConstraint() {
        val constraintLayout: ConstraintLayout? = activity?.findViewById(R.id.content)
        val constraintSet = ConstraintSet()
        constraintSet.clone(constraintLayout)
        constraintSet.connect(
            R.id.nav_host_fragment,
            ConstraintSet.TOP,
            R.id.toolbar,
            ConstraintSet.BOTTOM,
            0
        )
        constraintSet.applyTo(constraintLayout)
    }

    private fun initializeSpinner() {
        activity?.let {
            ArrayAdapter.createFromResource(
                it,
                R.array.cameras_array,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                // Apply the adapter to the spinner
                spinner_cameras.adapter = adapter
            }
        }


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

    private fun initializeAdapter() {
        camerasAdapter = CameraAdapter(flag = 1)
        rv_cameras_list.apply {
            adapter = camerasAdapter

        }
    }

    override fun provideLayout() = R.layout.fragment_camera_vehicle_chooser

}