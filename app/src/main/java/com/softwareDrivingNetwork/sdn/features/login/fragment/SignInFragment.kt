package com.softwareDrivingNetwork.sdn.features.login.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.mobsandgeeks.saripaar.ValidationError
import com.mobsandgeeks.saripaar.Validator
import com.mobsandgeeks.saripaar.annotation.Email
import com.mobsandgeeks.saripaar.annotation.Password
import com.softwareDrivingNetwork.sdn.R
import com.softwareDrivingNetwork.sdn.common.*
import com.softwareDrivingNetwork.sdn.models.login.SignInBody
import kotlinx.android.synthetic.main.fragment_sign_in.*
import org.koin.android.viewmodel.ext.android.viewModel


class SignInFragment : BaseFragment(), Validator.ValidationListener {

    private val signInViewModel: LoginViewModel by viewModel()
    lateinit var tokenUser: String
    lateinit var userId: String


    @Password(min = 8, messageResId = R.string.short_password)
    private lateinit var password: TextInputEditText

    @Email(messageResId = R.string.enter_valid_email)
    private lateinit var email: TextInputEditText


    override fun provideLayout() = R.layout.fragment_sign_in

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity?)?.supportActionBar?.title =
            resources.getString(R.string.sign_in_toolbar_title)

        email = ed_username
        password = ed_password
        validator.setValidationListener(this)

        btn_login.setSafeOnClickListener { validator.validate() }

        tv_forget_password.setOnClickListener { findNavController().navigate(R.id.goToForgetPassword) }

        signInViewModel.signInResponse.observe(viewLifecycleOwner, Observer {
            dismissProgressDialog()
            if(it == "result") activity?.let { it1 -> Navigation.goToServicesActivityWithFinish(it1) }
            else Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
        })
        signInViewModel.errorResponse.observe(viewLifecycleOwner, Observer {
            dismissProgressDialog()
            Log.i("error", it.message.toString())
            Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
        })

    }

    override fun onValidationSucceeded() {
        showProgress()
        val email = email.text.toString()
        val password = password.text.toString()
        val signInBody = SignInBody(email = email, password = password)
        signInViewModel.signIn(signInBody)


    }

    override fun onValidationFailed(errors: MutableList<ValidationError>?) {
        errors?.let {
            for (error in it) {
                (error.view as EditText).error = error.getCollatedErrorMessage(context)
                til_password.disablePasswordToggle()
            }
        }
        ed_password.addTextChangedListener {
            til_password.enablePasswordToggle()
        }
    }
}