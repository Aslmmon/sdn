@file:Suppress("NAME_SHADOWING", "UNREACHABLE_CODE")

package com.softwareDrivingNetwork.sdn.common

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.Window
import android.widget.FrameLayout
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import com.softwareDrivingNetwork.sdn.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


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

fun String.stringify(): String {
    val gson = Gson()
    return gson.toJson(this)
}

fun Context.showCustomAlertDialog( onYesClicked: () -> Unit) {
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




