package com.example.roombd_muti_25.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Music::class],
    version = 1
)
abstract class MusicDB : RoomDatabase(){

    abstract fun musicDao() : MusicDao

    companion object {

        @Volatile private var instance : MusicDB? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also{
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            MusicDB::class.java,
            "Music.db"
        ).build()
    }
}

