package com.softwareDrivingNetwork.sdn.common

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.softwareDrivingNetwork.sdn.features.drawer_tabs.cameras.CamerasActivity
import com.softwareDrivingNetwork.sdn.features.drawer_tabs.drivers.DriversActivity
import com.softwareDrivingNetwork.sdn.features.drawer_tabs.vehicles.VehiclesActivity
import com.softwareDrivingNetwork.sdn.features.home.MainActivity
import com.softwareDrivingNetwork.sdn.features.drawer_tabs.history.HistoryTracks
import com.softwareDrivingNetwork.sdn.features.login.LoginActivity
import com.softwareDrivingNetwork.sdn.features.services_chosen.ServicesChosenActivity

object Navigation {

    fun goToMainActivityWithFinish(ctx: Context) {
        (ctx as Activity).startActivity(Intent(ctx, MainActivity::class.java))
        ctx.finish()
    }
    fun goToMainActivity(ctx: Context) {
        (ctx as Activity).startActivity(Intent(ctx, MainActivity::class.java))
    }

    fun goToServicesActivityWithFinish(ctx: Context) {
        (ctx as Activity).startActivity(Intent(ctx,ServicesChosenActivity::class.java))
        ctx.finish()
    }
    fun goToLoginActivityWithFinish(ctx: Context) {
        val intent = Intent(ctx, LoginActivity::class.java)
        ctx.startActivity(intent)
        (ctx as Activity).finish()
    }

    fun goToHistoryActivityWithFinish(ctx: Context) {
        val intent = Intent(ctx, HistoryTracks::class.java)
        ctx.startActivity(intent)
    }


    fun goToVehiclesActivity(ctx: Context) {
        val intent = Intent(ctx, VehiclesActivity::class.java)
        ctx.startActivity(intent)
    }

    fun goToCamerasActivity(ctx: Context) {
        val intent = Intent(ctx, CamerasActivity::class.java)
        ctx.startActivity(intent)
    }

    fun goToDriversActivity(ctx: Context) {
        val intent = Intent(ctx, DriversActivity::class.java)
        ctx.startActivity(intent)
    }
}