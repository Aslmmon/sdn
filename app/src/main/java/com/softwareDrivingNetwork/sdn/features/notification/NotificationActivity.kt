package com.softwareDrivingNetwork.sdn.features.notification

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import com.softwareDrivingNetwork.sdn.R
import com.softwareDrivingNetwork.sdn.common.BaseActivity
import com.softwareDrivingNetwork.sdn.features.drawer_tabs.vehicles.VehiclesViewModel
import com.softwareDrivingNetwork.sdn.features.notification.adapter.NotificationRecycler
import kotlinx.android.synthetic.main.activity_notification.*
import org.koin.android.viewmodel.ext.android.viewModel

class NotificationActivity : BaseActivity() {
    private val vehiclesViewModel: VehiclesViewModel by viewModel()
    lateinit var notificationAdapter: NotificationRecycler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initializeAdapter()

        showNotification()
        vehiclesViewModel.notificationResponse.observe(this, Observer {
            dismissProgressDialog()
            notificationAdapter.submitList(it.data)
        })

    }

    private fun showNotification() {
        showProgress()
        vehiclesViewModel.getNotification(getStringifiedData()!!)
    }

    private fun initializeAdapter() {
        notificationAdapter = NotificationRecycler()
        rv_notification.adapter = notificationAdapter
    }

    override fun provideLayout() = R.layout.activity_notification

    override fun passNameToActivity(): String? = resources.getString(R.string.notfication_title)
}