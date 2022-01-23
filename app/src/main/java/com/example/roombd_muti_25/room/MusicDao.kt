package com.example.roombd_muti_25.room

import androidx.room.*

@Dao
interface MusicDao {

    @Insert
    suspend fun addMusic(music: Music)

    @Update
    suspend fun updateMusic(music: Music)

    @Delete
    suspend fun deleteMusic(music: Music)

    @Query ("SELECT * FROM music")
    suspend fun getMusic():List<Music>

    @Query( "SELECT * FROM music WHERE id=:music_id")
    suspend fun getMusics(music_id: Int): List<Music>

}