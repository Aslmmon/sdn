package com.example.salmtech.common.di

import com.softwareDrivingNetwork.sdn.core.api.GeneralApis
import com.softwareDrivingNetwork.sdn.core.api.LoginApi
import com.softwareDrivingNetwork.sdn.core.network.create8102NetworkClient
import org.koin.dsl.module
import retrofit2.Retrofit


/**
 * Created by Aslm on 1/1/2020
 */

val retrofit8110: Retrofit = create8102NetworkClient()
private val LOGIN: LoginApi = retrofit8110.create(LoginApi::class.java)
private val GENERAL: GeneralApis = retrofit8110.create(GeneralApis::class.java)


val networkModule = module {
    factory { LOGIN }
    factory { GENERAL }


}