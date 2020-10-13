package com.softwareDrivingNetwork.sdn.features.home

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout.SimpleDrawerListener
import androidx.navigation.Navigation.*
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import com.google.android.material.navigation.NavigationView
import com.softwareDrivingNetwork.sdn.R
import com.softwareDrivingNetwork.sdn.common.BaseActivity
import com.softwareDrivingNetwork.sdn.common.Navigation.goToCamerasActivity
import com.softwareDrivingNetwork.sdn.common.Navigation.goToDriversActivity
import com.softwareDrivingNetwork.sdn.common.Navigation.goToHistoryActivityWithFinish
import com.softwareDrivingNetwork.sdn.common.Navigation.goToLoginActivityWithClearFlags
import com.softwareDrivingNetwork.sdn.common.Navigation.goToNotificationActivity
import com.softwareDrivingNetwork.sdn.common.Navigation.goToVehiclesActivity
import com.softwareDrivingNetwork.sdn.common.showCustomAlertDialog
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    val END_SCALE = 0.7f


    lateinit var appBarConfiguration: AppBarConfiguration
    override fun provideLayout() = R.layout.activity_main
    override fun passNameToActivity(): String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        init()
        naviagtionDrawer()
    }

    private fun init() {

        val navController = findNavController(this, R.id.nav_host_fragment)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.cameraVehicleChooser ,R.id.nav_live_track
            )
        )
       // NavigationUI.setupActionBarWithNavController(this, navController)

        toolbar.setupWithNavController(navController, appBarConfiguration)
         setSupportActionBar(toolbar)//needs to be after binding
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setHomeButtonEnabled(false)
        toolbar?.navigationIcon = null
        navigation_view.setNavigationItemSelectedListener(this)
    }

    private fun naviagtionDrawer() {
        navigation_view.bringToFront()
        navigation_view.setCheckedItem(R.id.nav_live_track)
        menu_icon.setOnClickListener {
            if (drawer_layout.isDrawerVisible(GravityCompat.START)) drawer_layout.closeDrawer(
                GravityCompat.START
            ) else drawer_layout.openDrawer(GravityCompat.START)
        }
        animateNavigationDrawer()
    }

    private fun animateNavigationDrawer() {
        drawer_layout.addDrawerListener(object : SimpleDrawerListener() {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                // Scale the View based on current slide offset
                val diffScaledOffset: Float = slideOffset * (1 - END_SCALE)
                val offsetScale = 1 - diffScaledOffset
                content.scaleX = offsetScale
                content.scaleY = offsetScale

                // Translate the View, accounting for the scaled width
                val xOffset: Float = drawerView.width * slideOffset
                val xOffsetDiff: Float = content.width * diffScaledOffset / 2
                val xTranslation = xOffset - xOffsetDiff
                content.translationX = xTranslation
            }
        })
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerVisible(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else super.onBackPressed()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_history_track -> goToHistoryActivityWithFinish(this)
            R.id.log_out -> showCustomAlertDialog(onYesClicked = { if (clearAllSavedLocalData()) goToLoginActivityWithClearFlags(this) })

            R.id.vehicles -> goToVehiclesActivity(this)
            R.id.cameras -> goToCamerasActivity(this)
            R.id.drivers -> goToDriversActivity(this)
            R.id.nav_notification -> goToNotificationActivity(this)
        }
        closeDrawer()
        return true
    }

    private fun closeDrawer() {
        drawer_layout.closeDrawer(GravityCompat.START)
    }

}