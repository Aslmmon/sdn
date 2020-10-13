package com.softwareDrivingNetwork.sdn.features.home.fragments.live

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.softwareDrivingNetwork.sdn.R
import com.softwareDrivingNetwork.sdn.common.BaseFragment
import com.softwareDrivingNetwork.sdn.features.home.fragments.SharedViewModel


class LiveTracking : BaseFragment(), OnMapReadyCallback {

    private val model: SharedViewModel by activityViewModels()
    lateinit var mMap: GoogleMap
     var positionNeeded:LatLng= LatLng(25.0,25.0)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        removeToolbar()
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        model.selected.observe(this, Observer {
            Toast.makeText(activity, it.lat.toString(), Toast.LENGTH_SHORT).show()
            positionNeeded = LatLng(it.lat, it.long)

        })


        val cameraPosition = CameraPosition.Builder()
            .target(positionNeeded)
            .zoom(15f).build()
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))

        mMap.addMarker(
            MarkerOptions().position(
                positionNeeded
            ).title("data").anchor(0.5f, 0.5f)
        )


    }


//    private fun applyNewConstraint() {
//        val constraintLayout: ConstraintLayout? = activity?.findViewById(R.id.content)
//        val constraintSet = ConstraintSet()
//        constraintSet.clone(constraintLayout)
//        constraintSet.connect(
//            R.id.nav_host_fragment,
//            ConstraintSet.TOP,
//            constraintLayout!!.id,
//            ConstraintSet.TOP,
//            0
//        )
//        constraintSet.applyTo(constraintLayout)
//    }

    override fun provideLayout() = R.layout.fragment_live_tracking

}