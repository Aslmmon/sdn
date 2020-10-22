package com.softwareDrivingNetwork.sdn.features.home.fragments.live

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.github.nkzawa.emitter.Emitter
import com.github.nkzawa.socketio.client.Socket
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.sdn.aivimapandroid.map.AiviMapCreator
import com.sdn.aivimapandroid.map.AiviMapFragment
import com.softwareDrivingNetwork.sdn.SDNApp
import com.softwareDrivingNetwork.sdn.common.Constants
import com.softwareDrivingNetwork.sdn.features.home.fragments.SharedViewModel
import com.softwareDrivingNetwork.sdn.models.User
import com.softwareDrivingNetwork.sdn.models.login.SignInBody
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONObject
import org.koin.android.ext.android.inject


class LiveTracking : AiviMapFragment() {
    lateinit var mSocket: Socket
    lateinit var jobId: JSONObject
    val TAG = "socket"
    lateinit var userObject: JSONObject
    var initialLatlng: LatLng? = null
    var unitId: String? = null
    var listOfLatlngs = mutableListOf<LatLng>()
    private val sharedPreferences: SharedPreferences by inject()
    var gson = Gson()
    private val model: SharedViewModel by activityViewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userObject = createUserObject()

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        model.selected.observe(viewLifecycleOwner, Observer {
            unitId = it.id
            Log.i("data", unitId!!)
            initialLatlng = LatLng(it.lat, it.long)
        })

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.toolbar?.visibility = View.GONE
        initializeSocket()


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
        Log.i(
            TAG,
            " onAactive ${it.get(0)}"
        )
        if (it.isNotEmpty()) {
            jobId = it[0] as JSONObject
            userObject.put("job_id", jobId.get("text"))
            val unitArray = JSONArray()
            unitArray.put(0, "2091023479")
            userObject.put("unitList", unitArray)
            mSocket.emit(Constants.SOCKET_UPDATE, userObject)
            val aiviMapCreator = AiviMapCreator.AiviMapBuilder(activity).setSpeed("2").build();
            //animateCameraFirstTime(initialLatlng,aiviMapCreator)

        }


    }

    private val onJobLocation = Emitter.Listener {
        Log.i(TAG, " OnJobLocation ${(it.get(0))}")
        if (it.isNotEmpty()) {
            val data = (it[0] as JSONObject).get("data") as JSONObject
            val latitude = data.getString("lat")
            val longtitude = data.getString("lng")
            val objectId = data.getString("objectId")
            val speedData = data.getString("speed")
            val sdnMileage = data.getString("sdn_mileage")
            val device_mileage = data.getString("dev_mileage")
            val date = data.getString("locTime")
            listOfLatlngs.add(LatLng(latitude.toDouble(), longtitude.toDouble()))

            val aiviMapCreator = AiviMapCreator.AiviMapBuilder(activity).setLatLngs(listOfLatlngs)
                .setSpecificLatLng(LatLng(latitude.toDouble(), longtitude.toDouble()))
                .setSpeed(speedData).setDevice_mileage(device_mileage).setSDN_mileage(sdnMileage)
                .setId(objectId)
                .setDate(date).build()

            Log.i("car", aiviMapCreator.speed.toString())



            showPathOfLocations(aiviMapCreator)
        }
    }

}

