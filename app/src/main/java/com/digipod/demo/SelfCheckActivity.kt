package com.digipod.demo

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.digipod.demo.fragments.FragmentHome
import kotlinx.android.synthetic.main.activity_appointment.*
import kotlinx.android.synthetic.main.activity_self_check.*
import kotlinx.android.synthetic.main.activity_self_check.btnBack
import kotlinx.android.synthetic.main.fragment_home.*

class SelfCheckActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_self_check)
        btnBack.setOnClickListener {
            val intent = Intent(this, FragmentHome::class.java)
            startActivity(intent)
        }
        vide1.setOnClickListener {
            openVideo("https://youtu.be/XKtTymNkcj0")
        }
        vide2.setOnClickListener {
            openVideo("https://youtu.be/PMzvbztSFI8")
        }
        vide3.setOnClickListener {
            openVideo("https://youtu.be/24aXN8ZLQOU")
        }
        ivSelf1.setOnClickListener {
            openVideo("https://www.hopkinsmedicine.org/health/conditions-and-diseases/how-to-tell-if-you-have-a-hernia")
        }
        ivSelf2.setOnClickListener {
            openVideo("https://www.depression.org.nz/is-it-depression-anxiety/self-test/")
        }
       ivSelf3.setOnClickListener {
            openVideo("https://my.clevelandclinic.org/health/diagnostics/22801-appendicitis-test#:~:text=How%20do%20you%20check%20for,and%20rotate%20your%20right%20hip")
        }
        ivSelf4.setOnClickListener {
            openVideo("https://www.mayoclinic.org/tests-procedures/breast-exam/about/pac-20393237#:~:text=Face%20forward%20and%20look%20for,of%20your%20hands%20pressed%20together")
        }

    }
    private fun openVideo(url: String) {
        val uri = Uri.parse(url)
        startActivity(Intent(Intent.ACTION_VIEW, uri))
    }
}