package com.softwareDrivingNetwork.sdn.common

import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.softwareDrivingNetwork.sdn.models.User
import com.softwareDrivingNetwork.sdn.models.login.SignInBody
import org.koin.android.ext.android.inject

abstract class BaseActivity : AppCompatActivity() {
    lateinit var loadingDialog: CustomProgress
    private val sharedPrefsEditor: SharedPreferences.Editor by inject()
    private val sharedPreferences: SharedPreferences by inject()
    var gson = Gson()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(provideLayout())
        if (passNameToActivity() != null) {
            supportActionBar?.title = passNameToActivity()
            showBackButton()
        }
        //   setTheme(R.style.Theme_MyAwesomeApp_Blue)
        loadingDialog = CustomProgress()

    }


    abstract fun provideLayout(): Int
    abstract fun passNameToActivity(): String?

    fun showProgress() {
        loadingDialog.show(this, "Loading ...")
    }

    fun dismissProgressDialog() {
        loadingDialog.dismissDialog()
    }

    fun showBackButton() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true);
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) onBackPressed()
        return super.onOptionsItemSelected(item)
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

    fun getUserData(): User? {
        val gson = GsonBuilder().create()
        val json = sharedPreferences.getString(Constants.USER_DATA, "")
        return gson.fromJson(json, User::class.java)
    }

    fun getStringifiedData(): String? {
        val signInBody = SignInBody(token = getUserData()?.token, _userid = getUserData()?._userId)
        return stringify(signInBody)
    }

//    fun getStringifiedDataForHistoryTracking(): String? {
//        val signInBody = SignInBody(
//            token = getUserData()?.token,
//            _userid = getUserData()?._userId,
//            start_time = "2020-10-27T09:36:31.000Z",
//            end_time = "2020-10-27T10:36:31.000Z",
//            playmode = true,
//            objectids = mutableListOf("4120105841"),
//            start = 0,
//            limit = 500,
//            min_speed = 0
//        )
//        return stringify(signInBody)
//    }

    fun <T> stringify(data: T): String? {
        return gson.toJson(data)
    }

    fun clearAllSavedLocalData(): Boolean {
        return sharedPrefsEditor.clear().commit()
    }


}