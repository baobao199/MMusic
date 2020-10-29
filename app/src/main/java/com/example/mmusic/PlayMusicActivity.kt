package com.example.mmusic

import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class PlayMusicActivity : AppCompatActivity(){

    private lateinit var mediaPlayer:MediaPlayer
    private lateinit var playlistMap:List<MediaPlayer>
    private lateinit var lastUri:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_playmusic)

        var uri = intent.extras?.getString("uri")
        mediaPlayer = MediaPlayer.create(this, Uri.parse(uri))
        mediaPlayer.start()
    }

    override fun onStop() {
        // send current play time
        // uri of the music back to the main activity
        mediaPlayer.stop()
        mediaPlayer.release()
        super.onStop()
    }
}