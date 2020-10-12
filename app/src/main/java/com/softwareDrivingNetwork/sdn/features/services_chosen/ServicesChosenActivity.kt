package com.softwareDrivingNetwork.sdn.features.services_chosen

import android.os.Bundle
import com.softwareDrivingNetwork.sdn.R
import com.softwareDrivingNetwork.sdn.common.BaseActivity
import com.softwareDrivingNetwork.sdn.common.Navigation
import kotlinx.android.synthetic.main.activity_services_chosen.*

class ServicesChosenActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.title = resources.getString(R.string.general_services_title)

        btn_fleet.setOnClickListener {
            Navigation.goToMainActivity(this)
        }
    }

    override fun provideLayout() = R.layout.activity_services_chosen

    override fun passNameToActivity() = null
}