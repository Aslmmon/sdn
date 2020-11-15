package com.softwareDrivingNetwork.sdn.features.drawer_tabs.vehicles

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.telephony.SmsManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.sdn.aivimapandroid.map.AiviUtils
import com.softwareDrivingNetwork.sdn.R
import com.softwareDrivingNetwork.sdn.common.BaseActivity
import com.softwareDrivingNetwork.sdn.features.drawer_tabs.vehicles.adapter.VehiclesAdapter
import com.softwareDrivingNetwork.sdn.models.general.vehicles.VehiclerModel
import kotlinx.android.synthetic.main.activity_vehicles.*
import org.koin.android.viewmodel.ext.android.viewModel


class VehiclesActivity : BaseActivity(), VehiclesAdapter.Interaction {
    private val vehiclesViewModel: VehiclesViewModel by viewModel()
    lateinit var vehiclesAdapter: VehiclesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()
        getVehiclesList()
        requestSmsPermission()

        vehiclesViewModel.vehiclesResponse.observe(this, Observer {
            dismissProgressDialog()
            if (it.type == "error") {
                Toast.makeText(this, it.text, Toast.LENGTH_SHORT).show()
            } else {
                vehiclesAdapter.submitList(it.data)
            }


        })

    }

    private fun initialize() {
        vehiclesAdapter = VehiclesAdapter(this)
        rv_vehicles.apply {
            layoutManager = LinearLayoutManager(this@VehiclesActivity)
            adapter = vehiclesAdapter
        }

    }

    private fun getVehiclesList() {
        showProgress()
        vehiclesViewModel.getVehiclersList(getStringifiedData()!!)
    }

    override fun provideLayout() = R.layout.activity_vehicles
    override fun passNameToActivity(): String? = resources.getString(R.string.vehicles_title)
    override fun onItemSelected(position: Int, item: VehiclerModel, isChecked: Boolean) {
        val builder = AlertDialog.Builder(this)
        if (isChecked) builder.setMessage("Do you want to cut of engine of this Vehicle ?")
        else builder.setMessage("Do you want to open engine of this Vehicle ?")

        builder.setPositiveButton("Yes") { dialog, id ->
            item.simNumber?.let { sendSMS(it, isChecked) }
        }
            .setNegativeButton(
                "No"
            ) { dialog, id -> //  Action for 'NO' Button
                dialog.cancel()
            }
        val alert: AlertDialog = builder.create()
        alert.setTitle("Cut Engine of This Vehicle ")
        alert.show()

    }


    fun sendSMS(simNumber: String, isClosed: Boolean) {
        if (isClosed) {
            val smsManager = SmsManager.getDefault() as SmsManager
            smsManager.sendTextMessage("$simNumber", null, "Relay,1#", null, null)
        } else {
            val smsManager = SmsManager.getDefault() as SmsManager
            smsManager.sendTextMessage("$simNumber", null, "Relay,0#", null, null)
        }

//        if (isClosed) {
//            /**
//             * open Car
//             */
//            val uri = Uri.parse("smsto:$simNumber")
//            val intent = Intent(Intent.ACTION_SENDTO, uri)
//            intent.putExtra("sms_body", "Relay,1#")
//            startActivity(intent)
//        } else {
//            /**
//             * close car
//             */
//            val uri = Uri.parse("smsto:$simNumber")
//            val intent = Intent(Intent.ACTION_SENDTO, uri)
//            intent.putExtra("sms_body", "Relay,0#")
//            startActivity(intent)
//        }
    }


    private fun requestSmsPermission() {
        val permission: String = Manifest.permission.SEND_SMS
        val grant = ContextCompat.checkSelfPermission(this, permission)
        if (grant != PackageManager.PERMISSION_GRANTED) {
            val permission_list = arrayOfNulls<String>(1)
            permission_list[0] = permission
            ActivityCompat.requestPermissions(this, permission_list, 1)
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            } else {
                requestSmsPermission()
            }
        }
    }
}