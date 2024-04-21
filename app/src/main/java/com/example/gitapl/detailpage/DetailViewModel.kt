package com.example.gitapl.detailpage

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gitapl.API.Retrofit
import com.example.gitapl.local.Favourite
import com.example.gitapl.local.FavouriteDao
import com.example.gitapl.local.UserDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application) : AndroidViewModel(application) {
    private val user = MutableLiveData<Detail>()
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var userDao: FavouriteDao?
    private var userDb: UserDB?

    init {
        userDb = UserDB.getDatabase(application)
        userDao = userDb?.favouriteDao()
    }

    fun setDetail(username: String) {
        Retrofit.instance
            .getDetails(username)
            .enqueue(object : Callback<Detail> {
                override fun onResponse(call: Call<Detail>, response: Response<Detail>) {
                    if (response.isSuccessful) {
                        user.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<Detail>, t: Throwable) {
                    Log.d("Failure", t.message.toString())
                }
            })
    }

    fun getDetail(): LiveData<Detail> {
        return user
    }
    fun addFavourite(username: String, id: Int, avatarUrl: String){
        CoroutineScope(Dispatchers.IO).launch {
            var user = Favourite(
                username,
                id,
                avatarUrl
            )
            userDao?.addFavourite(user)
        }
    }
    suspend fun checkUser(id: Int): Any {
        return userDao?.checkUser(id) ?: 0
    }

    fun removeFromFavourite(id: Int){
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.removeFromFavourite(id)
        }
    }
}

