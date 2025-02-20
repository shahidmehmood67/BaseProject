package com.sm.android.baseproject.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey val id: Int,
    val name: String,
    val email: String
)