package com.softwareDrivingNetwork.sdn.core.di

import com.softwareDrivingNetwork.sdn.core.repo.GeneralRepo
import com.softwareDrivingNetwork.sdn.core.repo.LoginRepo
import org.koin.dsl.module

/**
 * Created by Aslm on 1/1/2020
 */

val repositoriesModule = module {
    factory{ LoginRepo(get()) }
    factory { GeneralRepo(get ()) }

}