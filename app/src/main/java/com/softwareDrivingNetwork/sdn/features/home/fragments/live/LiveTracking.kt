package com.softwareDrivingNetwork.sdn.features.home.fragments.live

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.github.nkzawa.emitter.Emitter
import com.github.nkzawa.socketio.client.Socket
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.sdn.aivimapandroid.map.MapFragment
import com.softwareDrivingNetwork.sdn.SDNApp
import com.softwareDrivingNetwork.sdn.common.Constants
import com.softwareDrivingNetwork.sdn.models.User
import com.softwareDrivingNetwork.sdn.models.login.SignInBody
import com.softwareDrivingNetwork.sdn.models.socket.SocketActiveModel
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONObject
import org.koin.android.ext.android.inject


class LiveTracking : MapFragment() {
    lateinit var mSocket: Socket
    lateinit var jobId: JSONObject
    val TAG = "socket"
    lateinit var userObject: JSONObject

    // var socketTracker = SocketTracker()
    private val sharedPreferences: SharedPreferences by inject()
    var gson = Gson()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userObject = createUserObject()
        initializeSocket()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.toolbar?.visibility = View.GONE


    }

    private fun initializeSocket() {
        val app: SDNApp = this.requireActivity().application as SDNApp
        mSocket = app.socket!!
        mSocket.on(Socket.EVENT_CONNECT_ERROR, onConnectError);
        mSocket.on(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
        mSocket.on(Socket.EVENT_CONNECT, onConnect);
        mSocket.on(Socket.EVENT_DISCONNECT, onDisconnect);
        mSocket.connect()
        mSocket.emit(Constants.SOCKET_START, userObject)
        mSocket.on(Constants.SOCKET_ACTIVE, onActive)
        mSocket.on(Constants.SOCKET_LOCATION, onJobLocation)


    }

    private fun createUserObject(): JSONObject {
        val obj = JSONObject()
        obj.put("userid", "-200")
        obj.put("job_name", "active_location_job")
        obj.put("token", "${getStringifiedDataBody().token}")
        obj.put("_userid", "${getStringifiedDataBody()._userid}")
        obj.put("app_version", 49)
        return obj
    }

    fun getUserData(): User? {
        val gson = GsonBuilder().create()
        val json = sharedPreferences.getString(Constants.USER_DATA, "")
        return gson.fromJson(json, User::class.java)
    }

    fun getStringifiedDataBody(): SignInBody {
        val signInBody = SignInBody(token = getUserData()?.token, _userid = getUserData()?._userId)
        return signInBody
    }


    var onConnect = Emitter.Listener {
        Log.i(
            TAG,
            "connected to Socket "
        )
    }

    private val onConnectError = Emitter.Listener {
        Log.i(
            TAG,
            "Error connecting ${it.get(0)}"
        )
    }

    override fun onDetach() {
        mSocket.disconnect()
        mSocket.close()
        listenOffFromSockets()
        super.onDetach()
    }

    private fun listenOffFromSockets() {
        mSocket.off(Constants.SOCKET_ACTIVE, onActive)
        mSocket.off(Constants.SOCKET_LOCATION, onJobLocation)
    }

    private val onDisconnect = Emitter.Listener {
        Log.i(
            TAG,
            "onDisconnect from Socket ${it.get(0)}"
        )
    }

    private val onActive = Emitter.Listener {
        jobId = it.get(0) as JSONObject
        userObject.put("job_id", jobId.get("text"))
        val unitArray = JSONArray()
        unitArray.put(0, "4120105841")
        userObject.put("unitList", unitArray)
        mSocket.emit(Constants.SOCKET_UPDATE, userObject)

    }

    private val onJobLocation = Emitter.Listener {
        val data = (it.get(0) as JSONObject).get("data") as JSONObject
        val latitude = data.getString("lat")
        val longtitude = data.getString("lng")
        var LatLng = LatLng(latitude.toDouble(), longtitude.toDouble())
        Log.i(
            TAG,
            " OnJobLocation ${(it.get(0) as JSONObject).get("data")}"
        )
    }

}