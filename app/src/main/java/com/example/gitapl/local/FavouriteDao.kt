package com.example.gitapl.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface FavouriteDao {

    @Insert
    fun addFavourite(favourite: Favourite)

    @Query("SELECT * FROM favourite_user")
    fun getFavouriteUser(): LiveData<List<Favourite>>

    @Query("SELECT count(*) FROM favourite_user WHERE favourite_user.id = :id")
    fun checkUser(id: Int): Int

    @Query("DELETE FROM favourite_user WHERE favourite_user.id = :id")
    fun removeFromFavourite(id: Int): Int
}