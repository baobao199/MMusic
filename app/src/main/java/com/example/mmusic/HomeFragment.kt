package com.example.mmusic

import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_home.*
import java.io.File

class HomeFragment : Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var gpath: String = Environment.getExternalStorageDirectory().absolutePath
        var spath = "Download"
        var fullpath = File(gpath + File.separator + spath)

        val arrList = mutableListOf<Music>()
        ReadSongs(fullpath,arrList)

        // RecyclerView node initialized here
        recyclerViewListMusic.apply {
            val listMusic = ListMusicAdapter(arrList)
            adapter = listMusic
            listMusic.onClick = {music->
                activity?.let {
                    val viewmodel = ViewModelProviders.of(it).get(ActivityViewModel::class.java)
                    viewmodel.dataMusic.postValue(music)
                    viewmodel.isClicked.postValue(true)
                }
            }
            layoutManager = LinearLayoutManager(activity)
            listMusic.notifyDataSetChanged()
        }

    }

    fun ReadSongs(root: File,arrList:MutableList<Music>) {
        val fileList: ArrayList<File> = ArrayList()
        val listAllFiles = root.listFiles()
        val listMusic = mutableListOf<String>()
        if (listAllFiles != null && listAllFiles.size > 0) {
            for (currentFile in listAllFiles) {
                if (currentFile.name.endsWith(".mp3")) {
                    fileList.add(currentFile.absoluteFile)
                    listMusic.add(currentFile.getName())

                    val music = Music(currentFile.absolutePath,currentFile.name)
                    arrList.add(music)
                }
            }
        }
    }
}