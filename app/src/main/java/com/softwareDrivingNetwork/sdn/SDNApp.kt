package com.softwareDrivingNetwork.sdn

import android.app.Application
import android.content.Context
import com.example.salmtech.common.di.networkModule
import com.softwareDrivingNetwork.sdn.core.di.sharedPref
import com.softwareDrivingNetwork.sdn.core.di.repositoriesModule
import com.softwareDrivingNetwork.sdn.core.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class SDNApp : Application() {

    companion object {
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext

        startKoin {
            androidContext(this@SDNApp)
            androidLogger()
            modules(listOf(viewModelModule, networkModule, repositoriesModule, sharedPref))
        }
    }
}
