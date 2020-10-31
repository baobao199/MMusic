package com.example.mmusic

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*

class MainActivity : AppCompatActivity() {

    lateinit var homeFragment: HomeFragment
    lateinit var searchFragment: SearchFragment
    private lateinit var mediaPlayer:MediaPlayer
    private lateinit var currentPlayer :MediaPlayer
    private  var flagHold = false
    private var flagDown = false
    private var sharedViewmodel: ActivityViewModel?=null
    private var totalTime:Int = 0
    private val mOnNavigationItemSelectdListener = BottomNavigationView.OnNavigationItemSelectedListener { item->
        when(item.itemId){
            R.id.nav_home->{
                replaceFragment(HomeFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_search->{
                replaceFragment(SearchFragment())
                return@OnNavigationItemSelectedListener true
            }
            else -> false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottom_navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectdListener)
        replaceFragment(HomeFragment())
        currentPlayer = MediaPlayer()
        initView()
        handleOnClick()

        positionBar.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    if (fromUser) {
                        mediaPlayer.seekTo(progress)
                    }
                }

                override fun onStartTrackingTouch(p0: SeekBar?) {
                }

                override fun onStopTrackingTouch(p0: SeekBar?) {
                }

            })
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initView() {
        ivDetail.setOnTouchListener { view, motionEvent ->
            handleAnimationWithMotionEvent(motionEvent)
            return@setOnTouchListener true
        }
        llController.setOnTouchListener{ view ,motionEvent->
            handleAnimationWithMotionEvent(motionEvent)
            return@setOnTouchListener true
        }
        motionLayout.setOnTouchListener { view, motionEvent ->
            if(motionEvent.action == MotionEvent.ACTION_DOWN){
                flagDown = true
            }
            else if(motionEvent.action == MotionEvent.ACTION_MOVE){
                flagHold = true
            }
            else if(motionEvent.action == MotionEvent.ACTION_UP){
                flagHold = false
                flagDown = false
            }
            if(flagDown && flagHold && motionLayout.currentState == R.id.start){
                motionLayout.setTransition(R.id.jumpToEnd)
                motionLayout.transitionToEnd()
                flagHold = false
                flagDown = false
            }
            return@setOnTouchListener true
        }

    }
    private fun handleAnimationWithMotionEvent(motionEvent: MotionEvent){
        if(motionEvent.action == MotionEvent.ACTION_DOWN){
            flagDown = true
        }
        else if(motionEvent.action == MotionEvent.ACTION_MOVE){
            flagHold = true
        }
        else if(motionEvent.action == MotionEvent.ACTION_UP){
            flagHold = false
            flagDown = false
        }
        if(flagDown && flagHold && motionLayout.currentState == R.id.start){
            motionLayout.setTransition(R.id.jumpToEnd)
            motionLayout.transitionToEnd()
            flagHold = false
            flagDown = false
        }
        else if(flagDown && flagHold && motionLayout.currentState == R.id.end){
            motionLayout.setTransition(R.id.jumpToStart)
            motionLayout.transitionToEnd()
            flagHold = false
            flagDown = false
        }
    }

    private fun handleOnClick() {
        sharedViewmodel = ViewModelProviders.of(this).get(ActivityViewModel::class.java)

        sharedViewmodel?.dataMusic?.observe(this, Observer {
            motionLayout.setTransition(R.id.baseStateToStart)
            motionLayout.transitionToEnd()

            mediaPlayer = MediaPlayer.create(this, Uri.parse(it.mPath))
            if(currentPlayer.isPlaying){
                currentPlayer.stop()
                currentPlayer.reset()
                totalTime = mediaPlayer.duration
                currentPlayer = mediaPlayer
                initialiseSeekBar()
                currentPlayer.start()

            }
            else{
                currentPlayer = mediaPlayer
                totalTime = mediaPlayer.duration
                initialiseSeekBar()
                currentPlayer.start()

            }
            Log.d("DUY", mediaPlayer.isPlaying.toString())

        })
    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentTransactiont = supportFragmentManager.beginTransaction()
        fragmentTransactiont.replace(R.id.fragment_container,fragment)
        fragmentTransactiont.commit()
    }

    private fun initialiseSeekBar(){
        positionBar.max = totalTime
        val handler = android.os.Handler()
        handler.postDelayed(object:Runnable{
            override fun run() {
                try{
                    positionBar.progress = mediaPlayer.currentPosition
                    handler.postDelayed(this,1000)
                }catch (e:Exception){
                    positionBar.progress =0
                }
            }
        },0)
    }
}