package com.example.mmusic

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ListMusicAdapter(private val ListMusic: MutableList<Music>) : RecyclerView.Adapter<ListMusicAdapter.ListMusicViewHolder>() {
    var onClick : ((Music) -> Unit)? = null
    class ListMusicViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val tvListMusic:TextView = itemView.findViewById(R.id.tvListMusic)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListMusicViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.listmusic_item,parent,false)
        return ListMusicViewHolder(view)
    }

    override fun getItemCount(): Int {
        return  ListMusic.size
    }

    override fun onBindViewHolder(holder: ListMusicViewHolder, position: Int) {
        holder.tvListMusic.text = ListMusic[position].mName
        holder.tvListMusic.setOnClickListener {
            onClick?.invoke(ListMusic[position])
//            val sentPath = Intent(holder.itemView.context,PlayMusicActivity::class.java)
//                sentPath.putExtra("uri",ListMusic[position].mPath)
//
//            holder.itemView.context.startActivity(sentPath)
        }
    }
}