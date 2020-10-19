package com.softwareDrivingNetwork.sdn

import android.app.Application
import android.content.Context
import com.example.salmtech.common.di.networkModule
import com.github.nkzawa.engineio.client.transports.WebSocket
import com.github.nkzawa.socketio.client.IO
import com.github.nkzawa.socketio.client.Socket
import com.softwareDrivingNetwork.sdn.common.Constants
import com.softwareDrivingNetwork.sdn.core.di.repositoriesModule
import com.softwareDrivingNetwork.sdn.core.di.sharedPref
import com.softwareDrivingNetwork.sdn.core.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import java.net.URISyntaxException
import java.security.cert.X509Certificate
import javax.net.ssl.*

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
            val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {}

                override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {}

                override fun getAcceptedIssuers(): Array<X509Certificate> {
                    return arrayOf()
                }
            })
            val sslContext = SSLContext.getInstance("TLS")
            sslContext.init(null, trustAllCerts, null)
            val options = IO.Options()
            IO.setDefaultHostnameVerifier { hostname, session ->
                true
            }

            options.reconnection = true
            options.reconnectionAttempts = 5
            options.sslContext = sslContext
            options.forceNew = true
            options.timeout = 50000
            options.reconnectionDelay = 1000
            options.reconnectionAttempts = 1000
            options.transports = arrayOf(WebSocket.NAME)

            IO.socket(Constants.SERVER_URL, options)
        } catch (e: URISyntaxException) {
            throw RuntimeException(e)
        }
    }


}
