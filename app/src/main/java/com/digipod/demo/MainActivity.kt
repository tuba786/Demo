package com.digipod.demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.digipod.demo.fragments.FragmentHome
import com.digipod.demo.fragments.FragmentReminder
import com.digipod.demo.fragments.FragmentSymptomChecker
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setCurrentFragment(FragmentHome())

        bottomNavBar.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> setCurrentFragment(FragmentHome())
                R.id.nav_symptom -> setCurrentFragment(FragmentSymptomChecker())
                R.id.nav_reminder -> setCurrentFragment(FragmentReminder())
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