package com.softwareDrivingNetwork.sdn.features.home.fragments.history

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.button.MaterialButton
import com.softwareDrivingNetwork.sdn.R
import com.softwareDrivingNetwork.sdn.common.BaseFragment
import com.softwareDrivingNetwork.sdn.common.Constants
import com.softwareDrivingNetwork.sdn.common.Navigation
import com.softwareDrivingNetwork.sdn.features.drawer_tabs.vehicles.VehiclesViewModel
import com.softwareDrivingNetwork.sdn.features.home.MainActivity
import com.softwareDrivingNetwork.sdn.features.home.fragments.SharedViewModel
import com.softwareDrivingNetwork.sdn.features.home.fragments.TimeStart
import com.softwareDrivingNetwork.sdn.features.home.fragments.camera_vehicle_chooser.CameraVehicleChooser
import kotlinx.android.synthetic.main.fragment_history.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*


class HistoryFragment : BaseFragment() {

    private val vehiclesViewModel: VehiclesViewModel by viewModel()
    lateinit var startDate:String
    lateinit var endDate:String
    private val model: SharedViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val ft: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
        ft.replace(R.id.parent_fragment_container, CameraVehicleChooser.newInstance(1))
        ft.commit()


        return super.onCreateView(inflater, container, savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        hideBackbutton()

        ed_date.setOnClickListener {
            openDataPicker("1")
        }
        ed_to_date.setOnClickListener {
            openDataPicker("2")
        }


        tv_start.setOnClickListener {
            val startTime = ed_date.text.toString()
            val endTime = ed_to_date.text.toString()
            val itemStart = TimeStart(startTime,endTime)
            model.shareTime(itemStart)
//            vehiclesViewModel.getHistoryLocation(getStringifiedDataForHistoryTracking(startTime,endTime,"test")!!)

          //  Log.i("test", newDate.toString())
            val bundle = bundleOf(Constants.NAVIGATION to Constants.FROM_HISTORY_TRACKING_TAB)
            findNavController().navigate(R.id.goToLiveTracking,bundle)
        }
//        vehiclesViewModel.historyResponse.observe(viewLifecycleOwner, Observer {
//            Log.i("history", it.toString())
//        })
//        vehiclesViewModel.errorResponse.observe(viewLifecycleOwner, Observer {
//            Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
//        })


    }

    private fun openDataPicker(type: String) {
        val cal = Calendar.getInstance()

        val dateSetListener =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                val myFormat = "dd.MM.yyyy" // mention the format you need
                val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
                val sdf = SimpleDateFormat(myFormat, Locale.US)
                when (type) {
                    "1" -> {
                        ed_date.setText(sdf.format(cal.time))
                        startDate = format.format(cal.time)
                        Log.i("date",startDate)
                        openTimePicker("1")

                    }
                    "2" -> {
                        ed_to_date.setText(sdf.format(cal.time))
                        endDate = format.format(cal.time)
                        openTimePicker("2")
                    }
                }
                return@OnDateSetListener
            }
        val datePicker = DatePickerDialog(
            requireActivity(), dateSetListener,
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        )
        datePicker.datePicker.maxDate = System.currentTimeMillis()
        datePicker.show()


    }

    @SuppressLint("SimpleDateFormat")
    private fun openTimePicker(timer: String) {
        val cal = Calendar.getInstance()
        val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
            cal.set(Calendar.HOUR_OF_DAY, hour)
            cal.set(Calendar.MINUTE, minute)
            when (timer) {
                "1" -> ed_date.append(" " + SimpleDateFormat("hh:mm a").format(cal.time))
                "2" -> ed_to_date.append(" " + SimpleDateFormat("hh:mm a").format(cal.time))
            }

        }
        TimePickerDialog(
            requireActivity(),
            timeSetListener,
            cal.get(Calendar.HOUR_OF_DAY),
            cal.get(Calendar.MINUTE),
            true
        ).show()
    }

    override fun provideLayout(): Int = R.layout.fragment_history

    private fun convertToDate(dateNew: String) {

    }
}



