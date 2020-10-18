package com.softwareDrivingNetwork.sdn.features.home.fragments.live

import android.content.ContentValues.TAG
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import com.github.nkzawa.emitter.Emitter
import com.github.nkzawa.socketio.client.Socket
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.sdn.aivimapandroid.map.MapFragment
import com.softwareDrivingNetwork.sdn.SDNApp
import com.softwareDrivingNetwork.sdn.common.Constants
import com.softwareDrivingNetwork.sdn.models.User
import com.softwareDrivingNetwork.sdn.models.login.SignInBody
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject


class LiveTracking : MapFragment() {
    lateinit var mSocket: Socket

    // var socketTracker = SocketTracker()
    private val sharedPreferences: SharedPreferences by inject()
    var gson = Gson()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeSocket()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.toolbar?.visibility = View.GONE

        //initializeSocket()


    }

    private fun initializeSocket() {
        val app: SDNApp = this.requireActivity().application as SDNApp

        /**
         * solution :
         * https://nhancv.com/android-socket-io-client-v1-0-0-engineioexception-xhr-poll-error/
         */

        mSocket = app.socket!!
        mSocket.on(Socket.EVENT_CONNECT_ERROR, onConnectError);
        mSocket.on(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
        mSocket.on(Socket.EVENT_CONNECT, onConnect);
        mSocket.on(Socket.EVENT_DISCONNECT, onDisconnect);
        mSocket.connect()
//        if (mSocket.connected()) {
//            Toast.makeText(activity, "Connected", Toast.LENGTH_LONG).show()
//        } else {
//            Toast.makeText(activity, "Not Connected", Toast.LENGTH_SHORT).show()
//        }
    }

    fun getUserData(): User? {
        val gson = GsonBuilder().create()
        val json = sharedPreferences.getString(Constants.USER_DATA, "")
        return gson.fromJson(json, User::class.java)
    }

    fun getStringifiedData(): String? {
        val signInBody = SignInBody(token = getUserData()?.token, _userid = getUserData()?._userId)
        return stringify(signInBody)
    }

    fun <T> stringify(data: T): String? {
        return gson.toJson(data)
    }


    override fun onDestroy() {
        //mSocket.disconnect()
        //mSocket.close()
        super.onDestroy()

    }

    var onConnect = Emitter.Listener { Log.d(TAG, "Socket Connected!") }

    private val onConnectError = Emitter.Listener {
          //  Runnable {
                Log.e(
                    "error",
                    "Error connecting ${it.get(0).toString()}"
                )
            //}
    }
    private val onDisconnect = Emitter.Listener {
           //Runnable {
               Log.e(
                   "error",
                   "Error connecting onDisconnect ${it.get(0).toString()}"
               )
    //       }
    }
}