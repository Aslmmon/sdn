package com.softwareDrivingNetwork.sdn.common

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.mobsandgeeks.saripaar.Validator
import com.softwareDrivingNetwork.sdn.models.User
import com.softwareDrivingNetwork.sdn.models.login.SignInBody
import kotlinx.android.synthetic.main.activity_main.*
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
        hideBackButtonInToolbar()
        loadingDialog = CustomProgress()
        return inflater.inflate(provideLayout(), container, false)

    }

    fun  hideBackbutton(){
        (activity as AppCompatActivity?)!!.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        (activity as AppCompatActivity?)!!.supportActionBar?.setHomeButtonEnabled(false);
    }

    abstract fun provideLayout(): Int

    fun showProgress() {
        activity?.let { loadingDialog.show(it, "Loading ...") }
    }

    fun <T> stringify(data: T): String? {
        return gson.toJson(data)
    }

    fun getStringifiedDataForHistoryTracking(startTime:String,endTime:String,objectId:String): String? {
        val signInBody = SignInBody(
            token = getUserData()?.token,
            _userid = getUserData()?._userId,
            start_time = startTime,
            end_time = endTime,
            playmode = true,
            objectids = mutableListOf("$objectId"),
            start = 0,
            limit = 500,
            min_speed = 0
        )
        return stringify(signInBody)
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

    fun hideBackButtonInToolbar(){
        (activity as AppCompatActivity?)?.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        (activity as AppCompatActivity?)?.supportActionBar?.setHomeButtonEnabled(false)
        activity?.toolbar?.navigationIcon = null

    }
    fun getUserData(): User? {
        val gson = GsonBuilder().create()
        val json = sharedPreferences.getString(Constants.USER_DATA, "")
        return gson.fromJson(json, User::class.java)
    }
    fun getStringifiedData(): String? {
        val signInBody = SignInBody(token = getUserData()?.token, _userid = getUserData()?._userId)
        return stringify(signInBody)
    }
     fun showToolbar() {
        activity?.toolbar?.visibility = View.VISIBLE
    }
     fun removeToolbar() {
        activity?.toolbar?.visibility = View.GONE
    }

}