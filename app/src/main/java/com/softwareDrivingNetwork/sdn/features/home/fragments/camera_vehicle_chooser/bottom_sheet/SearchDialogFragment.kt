package com.softwareDrivingNetwork.sdn.features.home.fragments.camera_vehicle_chooser.bottom_sheet


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.softwareDrivingNetwork.sdn.R
import com.softwareDrivingNetwork.sdn.common.afterTextChanged
import com.softwareDrivingNetwork.sdn.common.setSafeOnClickListener
import com.softwareDrivingNetwork.sdn.features.home.fragments.SharedViewModel
import kotlinx.android.synthetic.main.fragment_search.*

public class SearchDialogFragment : BottomSheetDialogFragment() {

    private val model: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ed_search.afterTextChanged {
            model.search(it)
        }
        tv_search_done.setSafeOnClickListener {
            dismiss()
        }

    }
}

