package com.example.roombd_muti_25.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Music (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val desc: String
        )

