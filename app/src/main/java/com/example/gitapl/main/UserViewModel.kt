package com.example.gitapl.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.example.gitapl.API.Retrofit
import com.example.gitapl.Theme.SettingPreferences
import com.example.gitapl.mod.Response
import com.example.gitapl.mod.User
import retrofit2.Call
import retrofit2.Callback

class UserViewModel(private val preferences: SettingPreferences) : ViewModel() {
    val list = MutableLiveData<ArrayList<User>>()

    fun getTheme() = preferences.getThemeSetting().asLiveData().also {
        Log.d("MainActivity", "onCreate: started")
    }

    fun setSearch(query: String) {
        Retrofit.instance
            .getUsers(query)
            .enqueue(object : Callback<Response> {
                override fun onResponse(
                    call: Call<Response>,
                    response: retrofit2.Response<Response>
                ) {
                    if (response.isSuccessful) {
                        list.postValue(response.body()?.items)
                    }
                }
                override fun onFailure(call: Call<Response>, t: Throwable) {
                    Log.d("Failure", t.message.toString())
                }

            })
    }

    fun getUser(): LiveData<ArrayList<User>> {
        return list
    }
    class Factory(private val preferences: SettingPreferences) :
            ViewModelProvider.NewInstanceFactory() {

                override fun <T : ViewModel> create(modelClass: Class<T>): T =
                    UserViewModel(preferences) as T
            }
}