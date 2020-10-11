package com.softwareDrivingNetwork.sdn.features.drawer_tabs.cameras

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import com.softwareDrivingNetwork.sdn.R
import com.softwareDrivingNetwork.sdn.common.BaseActivity
import com.softwareDrivingNetwork.sdn.features.drawer_tabs.cameras.adapter.CameraAdapter
import com.softwareDrivingNetwork.sdn.features.drawer_tabs.vehicles.VehiclesViewModel
import kotlinx.android.synthetic.main.activity_cameras.*
import org.koin.android.viewmodel.ext.android.viewModel

class CamerasActivity : BaseActivity() {
    lateinit var camerasAdapter: CameraAdapter
    private val vehiclesViewModel: VehiclesViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeAdapter()
        getCameraList()
        vehiclesViewModel.camerasResponse.observe(this, Observer {
            dismissProgressDialog()
            camerasAdapter.submitList(it.data)

        })

        vehiclesViewModel.errorResponse.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })
    }

    private fun getCameraList() {
        showProgress()
        vehiclesViewModel.getCameraList(getStringifiedData()!!)
    }

    private fun initializeAdapter() {
        camerasAdapter = CameraAdapter()
        rv_cameras.let {
            it.adapter = camerasAdapter
        }
    }

    override fun provideLayout() = R.layout.activity_cameras

    override fun passNameToActivity(): String?  = resources.getString(R.string.cameras_title)

}