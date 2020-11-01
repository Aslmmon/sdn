package com.softwareDrivingNetwork.sdn.features.home.fragments.map

import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import com.softwareDrivingNetwork.sdn.features.drawer_tabs.vehicles.VehiclesViewModel
import com.softwareDrivingNetwork.sdn.features.home.fragments.SharedViewModel
import com.softwareDrivingNetwork.sdn.features.home.fragments.TimeStart
import com.softwareDrivingNetwork.sdn.models.User
import com.softwareDrivingNetwork.sdn.models.login.SignInBody
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import org.json.JSONArray
import org.json.JSONObject
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import java.lang.Runnable
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class MapFragment : AiviMapFragment() {
    var mSocket: Socket? = null
    lateinit var jobId: JSONObject
    var timeStart: TimeStart? = null
    val TAG = "socket"
    var argumentsRecieved: String? = null
    lateinit var userObject: JSONObject
    var initialLatlng: LatLng? = null
    var unitId: String? = null
    var listOfLatlngs = mutableListOf<LatLng>()
    private val sharedPreferences: SharedPreferences by inject()
    var gson = Gson()
    private val model: SharedViewModel by activityViewModels()
    private val vehiclesViewModel: VehiclesViewModel by viewModel()
    lateinit var mainHandler: Handler


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userObject = createUserObject()
        mainHandler = Handler(Looper.getMainLooper())

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        model.selected.observe(viewLifecycleOwner, Observer {
            unitId = it.id
            initialLatlng = LatLng(it.lat!!, it.long!!)

            when (argumentsRecieved) {
                Constants.FROM_LIVE_TRACKNG_TAB -> initializeSocket()
            }
        })

        model.timeListener.observe(viewLifecycleOwner, Observer {
            timeStart = it


            when (argumentsRecieved) {
                Constants.FROM_HISTORY_TRACKING_TAB -> {

                    vehiclesViewModel.getHistoryLocation(
                        getStringifiedDataForHistoryTracking(
                            timeStart?.startTime.toString(),
                            timeStart?.endTime.toString(),
                            unitId!!
                        )!!
                    )

                }
            }


        })
        return super.onCreateView(inflater, container, savedInstanceState)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.toolbar?.visibility = View.GONE
        argumentsRecieved = arguments?.getString(Constants.NAVIGATION)



        vehiclesViewModel.historyResponse.observe(viewLifecycleOwner, Observer {
            Log.i("history", it.toString())


            if (it.data.isNotEmpty()) {
                it.data.forEach {
                    listOfLatlngs.add(LatLng(it.lat, it.lng))
                }

                val aiviMapCreator =
                    AiviMapCreator.AiviMapBuilder(activity)
                        .setLatLngs(listOfLatlngs.distinct())
                        .setSpecificLatLng(LatLng(2.2, 2.2))
                        .setSpeed("25").setDevice_mileage("25")
                        .setSDN_mileage("sdnMileage")
                        .setId("objectId")
                        .setDate("date").build()

                showPathOfLocationsWithDelay(aiviMapCreator)
            }

        })


        vehiclesViewModel.errorResponse.observe(viewLifecycleOwner, Observer {
            Log.i("history", it)
            Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
        })


    }

    private fun initializeSocket() {
        val app: SDNApp = this.requireActivity().application as SDNApp
        mSocket = app.socket!!
        listenForSocketListeners()
        mSocket?.connect()
        mSocket?.emit(Constants.SOCKET_START, userObject)


    }

    private fun listenForSocketListeners() {
        mSocket?.on(Socket.EVENT_CONNECT_ERROR, onConnectError);
        mSocket?.on(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
        mSocket?.on(Socket.EVENT_CONNECT, onConnect);
        mSocket?.on(Socket.EVENT_DISCONNECT, onDisconnect);
        mSocket?.on(Constants.SOCKET_ACTIVE, onActive)
        mSocket?.on(Constants.SOCKET_LOCATION, onJobLocation)
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
        if (mSocket != null && mSocket!!.connected()) {
            mSocket?.disconnect()
            mSocket?.close()
            listenOffFromSockets()
        }

        super.onDetach()
    }

    private fun listenOffFromSockets() {
        mSocket?.off(Constants.SOCKET_ACTIVE, onActive)
        mSocket?.off(Constants.SOCKET_LOCATION, onJobLocation)
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
            unitArray.put(0, unitId)
            userObject.put("unitList", unitArray)
            mSocket?.emit(Constants.SOCKET_UPDATE, userObject)
            val aiviMapCreator =
                AiviMapCreator.AiviMapBuilder(activity).setSpecificLatLng(initialLatlng).build();
            showCurrentLocationOnMap(initialLatlng, aiviMapCreator)

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

            val aiviMapCreator =
                AiviMapCreator.AiviMapBuilder(activity).setLatLngs(listOfLatlngs.distinct())
                    .setSpecificLatLng(LatLng(latitude.toDouble(), longtitude.toDouble()))
                    .setSpeed(speedData).setDevice_mileage(device_mileage)
                    .setSDN_mileage(sdnMileage)
                    .setId(objectId)
                    .setDate(date).build()

            showPathOfLocations(aiviMapCreator)
        }
    }

    fun <T> stringify(data: T): String? {
        return gson.toJson(data)
    }

    fun getStringifiedDataForHistoryTracking(
        startTime: String,
        endTime: String,
        objectId: String
    ): String? {
//        val df1: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
//        val result1: Date = df1.parse(startTime)
//        val result2: Date = df1.parse(endTime)

        val signInBody = SignInBody(
            token = getUserData()?.token,
            _userid = getUserData()?._userId,
            start_time = startTime,
            end_time = endTime,
            playmode = true,
            objectids = mutableListOf(objectId),
            start = 0,
            limit = 500,
            min_speed = 0
        )
        return stringify(signInBody)
    }


}

