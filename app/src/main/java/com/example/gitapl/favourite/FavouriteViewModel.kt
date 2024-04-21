package com.example.gitapl.favourite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.gitapl.local.Favourite
import com.example.gitapl.local.FavouriteDao
import com.example.gitapl.local.UserDB

class FavouriteViewModel(application: Application): AndroidViewModel(application) {
    private var userDao: FavouriteDao?
    private var userDb: UserDB?

    init {
        userDb = UserDB.getDatabase(application)
        userDao = userDb?.favouriteDao()
    }
    fun getFavouriteUser(): LiveData<List<Favourite>>? {
        return userDao?.getFavouriteUser()
    }
}