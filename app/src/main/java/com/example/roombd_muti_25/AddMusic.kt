package com.example.roombd_muti_25

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.roombd_muti_25.room.Constant
import com.example.roombd_muti_25.room.Music
import com.example.roombd_muti_25.room.MusicDB
import kotlinx.android.synthetic.main.activity_add_music.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class AddMusic : AppCompatActivity() {

    val db by lazy { MusicDB(this) }
    private var musicId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_music)
        setupView()
        setupListener()
        musicId = intent.getIntExtra("intent_id", 0)
    }

    fun setupView(){
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val intentType = intent.getIntExtra("intent_type", 0)
        when (intentType) {
            Constant.TYPE_CREATE -> {
                updateButton.visibility = View.GONE
            }
            Constant.TYPE_READ -> {
                saveButton.visibility = View.GONE
                updateButton.visibility = View.GONE
                getMusic()
            }
            Constant.TYPE_UPDATE -> {
                saveButton.visibility = View.GONE
                getMusic()
            }
        }
    }

    fun setupListener(){
        saveButton.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch {
                db.musicDao().addMusic(
                    Music(0, editTextTitle.text.toString(),
                        editTextAlbum.text.toString())
                )

                finish()
            }
        }
        updateButton.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch {
                db.musicDao().updateMusic(
                    Music(musicId, editTextTitle.text.toString(),
                        editTextAlbum.text.toString())
                )

                finish()
            }
        }
    }

    fun getMusic(){
        musicId = intent.getIntExtra("intent_id", 0)
        CoroutineScope(Dispatchers.IO).launch {
            val musics = db.musicDao().getMusics( musicId )[0]
            editTextTitle.setText( musics.title )
            editTextAlbum.setText( musics.desc)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}