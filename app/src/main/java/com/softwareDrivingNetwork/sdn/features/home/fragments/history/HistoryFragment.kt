package com.softwareDrivingNetwork.sdn.features.home.fragments.history

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.softwareDrivingNetwork.sdn.R
import com.softwareDrivingNetwork.sdn.common.BaseFragment
import com.softwareDrivingNetwork.sdn.features.drawer_tabs.vehicles.VehiclesViewModel
import kotlinx.android.synthetic.main.fragment_history.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class HistoryFragment : BaseFragment() {

    private val vehiclesViewModel: VehiclesViewModel by viewModel()
    lateinit var startDate:String
    lateinit var endDate:String

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
            val newDate = convertToDate(ed_date.text.toString())
            Log.i("test", newDate.toString())
           // findNavController().navigate(R.id.goToLiveTracking)
        }
        vehiclesViewModel.getHistoryLocation(getStringifiedDataForHistoryTracking()!!)
        vehiclesViewModel.historyResponse.observe(viewLifecycleOwner, Observer {
            Log.i("history", it.toString())
        })
        vehiclesViewModel.errorResponse.observe(viewLifecycleOwner, Observer {
            Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
        })


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
        DatePickerDialog(
            requireActivity(), dateSetListener,
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    @SuppressLint("SimpleDateFormat")
    private fun openTimePicker(timer: String) {
        val cal = Calendar.getInstance()
        val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
            cal.set(Calendar.HOUR_OF_DAY, hour)
            cal.set(Calendar.MINUTE, minute)
            when (timer) {
                "1" -> ed_date.append(" " + SimpleDateFormat("HH:mm").format(cal.time))
                "2" -> ed_to_date.append(" " + SimpleDateFormat("HH:mm").format(cal.time))
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

//        try {
//            val date = format.parse(dateNew)
//            System.out.println(date)
//        } catch (e: ParseException) {
//            e.printStackTrace()
//        }
    }
}



