package com.softwareDrivingNetwork.sdn.features.home.fragments.live

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import com.softwareDrivingNetwork.sdn.R
import com.softwareDrivingNetwork.sdn.common.BaseFragment
import kotlinx.android.synthetic.main.activity_main.*


class LiveTracking : BaseFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        removeToolbar()

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