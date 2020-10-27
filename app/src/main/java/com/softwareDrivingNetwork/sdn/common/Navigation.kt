package com.softwareDrivingNetwork.sdn.common

import android.app.Activity
import android.app.Notification
import android.content.Context
import android.content.Intent
import com.softwareDrivingNetwork.sdn.features.drawer_tabs.cameras.CamerasActivity
import com.softwareDrivingNetwork.sdn.features.drawer_tabs.drivers.DriversActivity
import com.softwareDrivingNetwork.sdn.features.drawer_tabs.vehicles.VehiclesActivity
import com.softwareDrivingNetwork.sdn.features.home.MainActivity
import com.softwareDrivingNetwork.sdn.features.login.LoginActivity
import com.softwareDrivingNetwork.sdn.features.notification.NotificationActivity
import com.softwareDrivingNetwork.sdn.features.services_chosen.ServicesChosenActivity

object Navigation {

    fun goToMainActivityWithFinish(ctx: Context) {
        (ctx as Activity).startActivity(Intent(ctx, MainActivity::class.java))
        ctx.finish()
    }

    fun goToMainActivity(ctx: Context) {
        (ctx as Activity).startActivity(Intent(ctx, MainActivity::class.java))
    }

    fun goToMainActivityFromHistroy(ctx: Context) {
        val intent = Intent(ctx,MainActivity::class.java)
        intent.putExtra(Constants.HISTORY_MAIN_NAVIGATION ,true)
        (ctx as Activity).startActivity(Intent(ctx, MainActivity::class.java))
    }

    fun goToServicesActivityWithFinish(ctx: Context) {
        (ctx as Activity).startActivity(Intent(ctx, ServicesChosenActivity::class.java))
        ctx.finish()
    }

    fun goToLoginActivityWithFinish(ctx: Context) {
        val intent = Intent(ctx, LoginActivity::class.java)
        ctx.startActivity(intent)
        (ctx as Activity).finish()
    }

    fun goToLoginActivityWithClearFlags(ctx: Context) {
        val intent = Intent(ctx, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or  Intent.FLAG_ACTIVITY_NEW_TASK)
        ctx.startActivity(intent)
        (ctx as Activity).finish()
    }

//    fun goToHistoryActivityWithFinish(ctx: Context) {
//        val intent = Intent(ctx, HistoryTracks::class.java)
//        ctx.startActivity(intent)
//    }


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

    fun goToNotificationActivity(ctx: Context) {
        val intent = Intent(ctx, NotificationActivity::class.java)
        ctx.startActivity(intent)
    }
}