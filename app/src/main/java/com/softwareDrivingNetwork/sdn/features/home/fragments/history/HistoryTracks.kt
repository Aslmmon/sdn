package com.softwareDrivingNetwork.sdn.features.home.fragments.history

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.softwareDrivingNetwork.sdn.R
import com.softwareDrivingNetwork.sdn.common.BaseActivity
import com.softwareDrivingNetwork.sdn.common.BaseFragment


class HistoryTracks : BaseActivity() {
    override fun provideLayout() = R.layout.fragment_history_tracks


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.title = resources.getString(R.string.history_tracking_title)
        showBackButton()


    }
}
