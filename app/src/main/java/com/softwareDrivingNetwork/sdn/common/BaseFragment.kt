package com.softwareDrivingNetwork.sdn.common

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.mobsandgeeks.saripaar.Validator
import com.softwareDrivingNetwork.sdn.R
import com.softwareDrivingNetwork.sdn.models.User
import org.koin.android.ext.android.inject

abstract class BaseFragment : Fragment() {
    lateinit var loadingDialog: CustomProgress
    var gson = Gson()
    lateinit var validator: Validator
    private val sharedPrefsEditor: SharedPreferences.Editor by inject()
    private val sharedPreferences: SharedPreferences by inject()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        validator = Validator(this)
        loadingDialog = CustomProgress()
        return inflater.inflate(provideLayout(), container, false)

    }

    abstract fun provideLayout(): Int

    fun showProgress() {
        activity?.let { loadingDialog.show(it, "Loading ...") }
    }

    fun <T> stringify(data: T): String? {
        return gson.toJson(data)
    }

    fun dismissProgressDialog() {
        loadingDialog.dismissDialog()
    }

    fun saveUserData(user: User) {
        val gson = GsonBuilder().create()
        val json = gson.toJson(
            User(
                name = user.name,
                token = user.token,
                _userId = user._userId,
                email = user.email
            )
        )
        sharedPrefsEditor.putString(Constants.USER_DATA, json).apply()
    }

    fun getUserData(user: User): User? {
        val gson = GsonBuilder().create()
        val json = sharedPreferences.getString(Constants.USER_DATA, "")
        return gson.fromJson(json, User::class.java)
    }
}