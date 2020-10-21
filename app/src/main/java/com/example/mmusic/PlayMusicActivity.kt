package com.example.mmusic

import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class PlayMusicActivity : AppCompatActivity(){

    private  lateinit var mediaPlayer:MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_playmusic)

        getIntentMethod()
    }
    private fun getIntentMethod(){
        var uri = intent.extras?.getString("uri")
        if(mediaPlayer != null && mediaPlayer.isPlaying){
            mediaPlayer.stop()
            mediaPlayer.release()
            mediaPlayer = MediaPlayer.create(this, Uri.parse(uri))
            mediaPlayer.start()
        }
        else{
            mediaPlayer = MediaPlayer.create(this, Uri.parse(uri))
            mediaPlayer.start()
        }
    }
}