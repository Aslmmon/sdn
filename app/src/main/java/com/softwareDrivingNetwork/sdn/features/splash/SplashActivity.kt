package com.softwareDrivingNetwork.sdn.features.splash

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.softwareDrivingNetwork.sdn.R
import com.softwareDrivingNetwork.sdn.common.BaseActivity
import com.softwareDrivingNetwork.sdn.common.Constants
import com.softwareDrivingNetwork.sdn.common.Constants.SPLASH_DELAY_TIME
import com.softwareDrivingNetwork.sdn.common.Navigation
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject


class SplashActivity : BaseActivity() {

    override fun passNameToActivity(): String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GlobalScope.launch {
            delay(SPLASH_DELAY_TIME)
            val user = getUserData()
            Log.i("data", user.toString())
            if (user?.token != null) Navigation.goToMainActivityWithFinish(this@SplashActivity)
            else Navigation.goToLoginActivityWithFinish(this@SplashActivity)

        }


    }

    override fun provideLayout() = R.layout.activity_splash
}