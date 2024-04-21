package com.example.gitapl.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "favourite_user")
data class Favourite (
            val login: String,
            @PrimaryKey
            val id: Int,
            val avatar_url: String
    ): Serializable