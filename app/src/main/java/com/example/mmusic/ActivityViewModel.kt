package com.example.mmusic

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ActivityViewModel():ViewModel(){
    val dataMusic = MutableLiveData<Music>()
    val dataListMusic = MutableLiveData<MutableList<Music>>()
}