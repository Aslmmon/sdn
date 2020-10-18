package com.softwareDrivingNetwork.sdn

import android.app.Application
import android.content.Context
import com.example.salmtech.common.di.networkModule
import com.github.nkzawa.socketio.client.IO
import com.github.nkzawa.socketio.client.Socket
import com.softwareDrivingNetwork.sdn.common.Constants
import com.softwareDrivingNetwork.sdn.core.di.sharedPref
import com.softwareDrivingNetwork.sdn.core.di.repositoriesModule
import com.softwareDrivingNetwork.sdn.core.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import java.net.URISyntaxException

class SDNApp : Application() {
    private var mSocket: Socket? = null
    val socket: Socket? get() = mSocket


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


        mSocket = try {
            val options = IO.Options()
            options.timeout = 5000
            options.reconnection = true
            options.reconnectionDelay  =1000
            options.reconnectionAttempts = 3
            IO.socket(Constants.SERVER_URL,options)
        } catch (e: URISyntaxException) {
            throw RuntimeException(e)
        }
    }


}
