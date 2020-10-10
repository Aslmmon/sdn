package com.softwareDrivingNetwork.sdn.features.home.fragments.live

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.softwareDrivingNetwork.sdn.R
import com.softwareDrivingNetwork.sdn.common.BaseFragment


class LiveTracking : BaseFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity?)?.supportActionBar?.title = resources.getString(R.string.live_tracking_title)

    }

    override fun provideLayout() = R.layout.fragment_live_tracking

}