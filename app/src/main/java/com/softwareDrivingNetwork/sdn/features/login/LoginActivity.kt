package com.softwareDrivingNetwork.sdn.features.login

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import com.softwareDrivingNetwork.sdn.R
import com.softwareDrivingNetwork.sdn.common.BaseActivity
import com.softwareDrivingNetwork.sdn.common.Navigation
import com.softwareDrivingNetwork.sdn.models.login.SignInBody
import kotlinx.android.synthetic.main.activity_login.*
import org.koin.android.viewmodel.ext.android.viewModel

class LoginActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        super.onCreate(savedInstanceState)

    }

    override fun provideLayout(): Int = R.layout.activity_login
}