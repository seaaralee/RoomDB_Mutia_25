package com.example.roombd_muti_25

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roombd_muti_25.room.Constant
import com.example.roombd_muti_25.room.Music
import com.example.roombd_muti_25.room.MusicDB
import kotlinx.android.synthetic.main.activity_add_music.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import android.widget.Toast.makeText as makeText1
import com.example.roombd_muti_25.MusicAdapter as MusicAdapter

class MainActivity : AppCompatActivity() {
    val db by lazy { MusicDB(this) }
    lateinit var musicAdapter: MusicAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupListener()
        setupRecyclerView()

    }


    override fun onStart() {
        super.onStart()
        loadMusic()
    }

    fun loadMusic(){
        CoroutineScope(Dispatchers.IO).launch {
            val musics = db.musicDao().getMusic()
            Log.d("MainActivity", "dbresponse: $musics")
            withContext(Dispatchers.Main){
                musicAdapter.setData(musics)
            }
        }
    }

    fun setupListener() {
        addFavMusic.setOnClickListener {
            intentEdit(0, Constant.TYPE_CREATE)
        }
    }

    fun intentEdit(musicId: Int, intentType: Int){
        startActivity(
            Intent(applicationContext, AddMusic::class.java)
                .putExtra("intent_id", musicId)
                .putExtra("intent_type", intentType)
        )
    }

    private fun setupRecyclerView() {
        musicAdapter = MusicAdapter(arrayListOf(), object : MusicAdapter.OnAdapterListener{
            override fun onClick(music: Music) {
                intentEdit(music.id, Constant.TYPE_READ)
            }

            override fun onUpdate(music: Music) {
                intentEdit(music.id, Constant.TYPE_UPDATE)
            }
            override fun onDelete(music: Music) {
                deleteDialog(music)
            }

        })
        showData.apply{
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = musicAdapter
        }
    }

    private fun deleteDialog(music: Music){
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.apply {
            setTitle("Alert")
            setMessage("Are you sure deleted ${music.title}?")
            setNegativeButton("Cancel") { dialoginterface, i ->
                dialoginterface.dismiss()
            }
            setPositiveButton("Delete") { dialoginterface, i ->
                dialoginterface.dismiss()
                CoroutineScope(Dispatchers.IO).launch {
                    db.musicDao().deleteMusic(music)
                    loadMusic()
                }
            }
        }
        alertDialog.show()
    }
}