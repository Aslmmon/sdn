@file:Suppress("NAME_SHADOWING", "UNREACHABLE_CODE")

package com.softwareDrivingNetwork.sdn.common

import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.EditText
import android.widget.FrameLayout
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.softwareDrivingNetwork.sdn.R
import com.softwareDrivingNetwork.sdn.SDNApp
import com.softwareDrivingNetwork.sdn.core.di.getSharedPrefrences
import com.softwareDrivingNetwork.sdn.models.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainCoroutineDispatcher
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject


fun ViewModel.launchDataLoad(
    execution: suspend CoroutineScope.() -> Unit,
    errorReturned: suspend CoroutineScope.(Throwable) -> Unit,
    finallyBlock: (suspend CoroutineScope.() -> Unit)? = null
) {

    this.viewModelScope.launch {
        try {
            execution()
        } catch (e: Throwable) {
            errorReturned(e)
        } finally {
            finallyBlock?.invoke(this)
        }
    }
}

fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }
    })
}

fun saveUserData(user: User) {
    val sharedPrefsEditor = getSharedPrefrences(androidApplication = SDNApp.context).edit()
    val gson = GsonBuilder().create()
    val json = gson.toJson(
        User(
            name = user.name,
            token = user.token,
            _userId = user._userId,
            email = user.email
        )
    )
    Log.i(
        "user", "User to be Saved" + User(
            name = user.name,
            token = user.token,
            _userId = user._userId,
            email = user.email
        ).toString()
    )
    sharedPrefsEditor.putString(Constants.USER_DATA, json).apply()
}

fun <T> stringify(data: T): String? {
    val gson = Gson()

    return gson.toJson(data)
}

fun Context.showCustomAlertDialog(onYesClicked: () -> Unit) {
    val dialog = Dialog(this)
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.setCancelable(false)
    dialog.setContentView(R.layout.custom_alert)
    dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//    dialog.window?.setBackgroundDrawable( ColorDrawable(Color.TRANSPARENT));
    dialog.window?.setBackgroundDrawableResource(android.R.color.transparent);

    val mDialogNo: MaterialButton = dialog.findViewById(R.id.no)
    mDialogNo.setOnClickListener {
        dialog.dismiss()
    }

    val mDialogOk: MaterialButton = dialog.findViewById(R.id.yes)
    mDialogOk.setOnClickListener {
        onYesClicked()
        dialog.cancel()
    }

    dialog.show()
}

fun TextInputLayout.enablePasswordToggle() {
    this.setPasswordVisibilityToggleTintMode(PorterDuff.Mode.MULTIPLY)
}

fun TextInputLayout.disablePasswordToggle() {
    this.setPasswordVisibilityToggleTintMode(PorterDuff.Mode.CLEAR)
}

fun View.setSafeOnClickListener(onSafeClick: (View) -> Unit) {
    val safeClickListener = SafeClickListener {
        onSafeClick(it)
    }
    setOnClickListener(safeClickListener)
}




