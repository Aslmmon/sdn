package com.softwareDrivingNetwork.sdn.features.home.fragments.live

import android.os.Bundle
import android.view.View
import com.sdn.aivimapandroid.map.MapFragment
import kotlinx.android.synthetic.main.activity_main.*


class LiveTracking : MapFragment() {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.toolbar?.visibility = View.GONE

    }



}