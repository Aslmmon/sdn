package com.softwareDrivingNetwork.sdn.features.drawer_tabs.history

import android.os.Bundle
import com.softwareDrivingNetwork.sdn.R
import com.softwareDrivingNetwork.sdn.common.BaseActivity


class HistoryTracks : BaseActivity() {
    override fun provideLayout() = R.layout.history_tracks
    override fun passNameToActivity(): String? =
        resources.getString(R.string.history_tracking_title)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }
}
