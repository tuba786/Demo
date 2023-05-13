package com.digipod.demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.digipod.demo.Fragments.FragmentHome
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setCurrentFragment(FragmentHome())

        bottomNavBar.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> setCurrentFragment(FragmentHome())
            }
            true
        }
    }
    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frameLayout_for_Fragments, fragment)
            commit()
        }
}