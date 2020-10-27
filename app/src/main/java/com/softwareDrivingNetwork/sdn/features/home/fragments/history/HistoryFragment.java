package com.softwareDrivingNetwork.sdn.features.home.fragments.history;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.softwareDrivingNetwork.sdn.R;
import com.softwareDrivingNetwork.sdn.common.BaseFragment;


public class HistoryFragment extends BaseFragment {



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        hideBackbutton();
    }

    @Override
    public int provideLayout()  {
        return R.layout.fragment_history;
    }

}