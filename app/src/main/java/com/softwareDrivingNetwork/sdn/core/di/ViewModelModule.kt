package com.softwareDrivingNetwork.sdn.core.di


import com.softwareDrivingNetwork.sdn.features.drawer_tabs.vehicles.VehiclesViewModel
import com.softwareDrivingNetwork.sdn.features.login.fragment.LoginViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by Aslm on 1/1/2020
 */

val viewModelModule = module {
    viewModel { LoginViewModel(get()) }
    viewModel { VehiclesViewModel(get()) }
}