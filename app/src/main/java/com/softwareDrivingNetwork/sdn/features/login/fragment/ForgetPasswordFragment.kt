package com.softwareDrivingNetwork.sdn.features.login.fragment

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.android.material.textfield.TextInputEditText
import com.mobsandgeeks.saripaar.ValidationError
import com.mobsandgeeks.saripaar.Validator
import com.mobsandgeeks.saripaar.annotation.Email
import com.softwareDrivingNetwork.sdn.R
import com.softwareDrivingNetwork.sdn.common.BaseFragment
import com.softwareDrivingNetwork.sdn.common.setSafeOnClickListener
import com.softwareDrivingNetwork.sdn.models.login.SignInBody
import kotlinx.android.synthetic.main.fragment_forget_password.*
import org.koin.android.viewmodel.ext.android.viewModel

class ForgetPasswordFragment : BaseFragment(), Validator.ValidationListener {
    private val signInViewModel: LoginViewModel by viewModel()


    override fun provideLayout() = R.layout.fragment_forget_password

    @Email(messageResId = R.string.enter_valid_email)
    private lateinit var email: TextInputEditText

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity?)?.supportActionBar?.title =
            resources.getString(R.string.reset_password_toolbar_title)



        email = ed_email
        validator.setValidationListener(this)
        btn_send.setSafeOnClickListener {
            validator.validate()
        }
        signInViewModel.resetPasswordResponse.observe(viewLifecycleOwner, Observer {
            dismissProgressDialog()
            Toast.makeText(activity, it.text, Toast.LENGTH_SHORT).show()
        })

        signInViewModel.errorResponse.observe(viewLifecycleOwner, Observer {
            dismissProgressDialog()
            Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
        })

    }

    override fun onValidationSucceeded() {
        showProgress()
        val email = ed_email.text.toString()
        val signInBody = SignInBody(email = email)
        signInViewModel.resetPassword(signInBody)
    }

    override fun onValidationFailed(errors: MutableList<ValidationError>?) {
        errors?.let {
            for (error in it) {
                (error.view as EditText).error = error.getCollatedErrorMessage(context)
            }
        }
    }
}