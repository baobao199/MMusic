package com.example.mmusic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var homeFragment: HomeFragment
    lateinit var searchFragment: SearchFragment

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
    }
    private fun replaceFragment(fragment: Fragment){
        val fragmentTransactiont = supportFragmentManager.beginTransaction()
        fragmentTransactiont.replace(R.id.fragment_container,fragment)
        fragmentTransactiont.commit()
    }
}