package com.softwareDrivingNetwork.sdn.features.home.fragments.history

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.softwareDrivingNetwork.sdn.R
import com.softwareDrivingNetwork.sdn.common.BaseFragment
import com.softwareDrivingNetwork.sdn.common.Constants
import com.softwareDrivingNetwork.sdn.common.Constants.FROM_EDIT_TEXT
import com.softwareDrivingNetwork.sdn.common.Constants.TO_EDIT_TEXT
import com.softwareDrivingNetwork.sdn.features.home.fragments.SharedViewModel
import com.softwareDrivingNetwork.sdn.features.home.fragments.TimeStart
import com.softwareDrivingNetwork.sdn.features.home.fragments.live_tracking.liveTracking
import kotlinx.android.synthetic.main.fragment_history.*
import java.text.SimpleDateFormat
import java.util.*


class HistoryFragment : BaseFragment() {

    lateinit var startDate: String
    lateinit var endDate: String
    var playSpeed: Int? = null
    var minimumSpeed: Int = 0

    private val model: SharedViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val ft: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
        ft.replace(R.id.parent_fragment_container, liveTracking.newInstance(1))
        ft.commit()


        return super.onCreateView(inflater, container, savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        hideBackbutton()

        ed_from_date.setOnClickListener {
            openDatePicker(FROM_EDIT_TEXT)
        }
        ed_to_date.setOnClickListener {
            openDatePicker(TO_EDIT_TEXT)
        }
        slider.addOnChangeListener { slider, value, fromUser ->
            playSpeed = value.toInt()
            Toast.makeText(activity, playSpeed.toString(), Toast.LENGTH_SHORT).show()
        }
        min_speed_slider.addOnChangeListener { slider, value, fromUser ->
            minimumSpeed = value.toInt()
            Toast.makeText(activity, minimumSpeed.toString(), Toast.LENGTH_SHORT).show()
        }

        tv_start.setOnClickListener {
            if (!::startDate.isInitialized || !::endDate.isInitialized) return@setOnClickListener
            val itemStart = TimeStart(startDate, endDate, playSpeed, minimumSpeed)
            model.shareTime(itemStart)
            val bundle = bundleOf(Constants.NAVIGATION to Constants.FROM_HISTORY_TRACKING_TAB)
            findNavController().navigate(R.id.goToLiveTracking, bundle)
        }


    }

    private fun openDatePicker(type: String) {
        val cal = Calendar.getInstance()

        val dateSetListener =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                val myFormat = "dd.MM.yyyy" // mention the format you need
                val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
                val sdf = SimpleDateFormat(myFormat, Locale.US)
                setDataToEditText(sdf, cal, format, type)

                return@OnDateSetListener
            }
        val datePicker = DatePickerDialog(
            requireActivity(), R.style.datepicker, dateSetListener,
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        )
        datePicker.datePicker.maxDate = System.currentTimeMillis()

        datePicker.show()


    }

    private fun setDataToEditText(
        sdf: SimpleDateFormat,
        cal: Calendar,
        format: SimpleDateFormat,
        type: String
    ) {
        when (type) {
            FROM_EDIT_TEXT -> {
                ed_from_date.setText(sdf.format(cal.time))
                startDate = format.format(cal.time)
                openTimePicker(FROM_EDIT_TEXT)
            }
            TO_EDIT_TEXT -> {
                ed_to_date.setText(sdf.format(cal.time))
                endDate = format.format(cal.time)
                openTimePicker(TO_EDIT_TEXT)
            }
        }

    }

    @SuppressLint("SimpleDateFormat")
    private fun openTimePicker(timer: String) {
        val cal = Calendar.getInstance()
        val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
            cal.set(Calendar.HOUR_OF_DAY, hour)
            cal.set(Calendar.MINUTE, minute)
            when (timer) {
                FROM_EDIT_TEXT -> ed_from_date.append(" " + SimpleDateFormat("hh:mm a").format(cal.time))
                TO_EDIT_TEXT -> ed_to_date.append(" " + SimpleDateFormat("hh:mm a").format(cal.time))
            }

        }
        TimePickerDialog(
            requireActivity(),
            R.style.datepicker,
            timeSetListener,
            cal.get(Calendar.HOUR_OF_DAY),
            cal.get(Calendar.MINUTE),
            true
        ).show()
    }

    override fun provideLayout(): Int = R.layout.fragment_history

}



