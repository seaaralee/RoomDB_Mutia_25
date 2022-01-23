package com.example.roombd_muti_25

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.roombd_muti_25.room.Music
import kotlinx.android.synthetic.main.list_music.view.*

class MusicAdapter(private val musics: ArrayList<Music>, private val listener: OnAdapterListener) : RecyclerView.Adapter<MusicAdapter.MusicViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicViewHolder {
        return MusicViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_music, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MusicViewHolder, position: Int) {
        val music  = musics[position]
        holder.view.textTitle.text = music.title
        holder.view.textTitle.setOnClickListener{
            listener.onClick( music )
        }
        holder.view.iconEdit.setOnClickListener{
            listener.onUpdate( music )
        }
        holder.view.iconDelete.setOnClickListener{
            listener.onDelete( music )
        }
    }

    override fun getItemCount() = musics.size

    class MusicViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    fun setData(list: List<Music>){
        musics.clear()
        musics.addAll(list)
        notifyDataSetChanged()
    }

    interface OnAdapterListener{
        fun onClick(music: Music)
        fun onUpdate(music: Music)
        fun onDelete(music: Music)
    }
}