package com.digipod.demo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.digipod.demo.fragments.FragmentHome
import kotlinx.android.synthetic.main.activity_appointment.*


class AppointmentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_appointment)
        btnBack.setOnClickListener {
            val intent = Intent(this, FragmentHome::class.java)
            startActivity(intent)
        }
        cdGenPhy.setOnClickListener {
            startDoctorActivity("General Physician")
        }
        cdSkin.setOnClickListener {
            startDoctorActivity("Skin and Hair")
        }
        cdWomenHealth.setOnClickListener {
            startDoctorActivity("Women's Health")
        }
        cdCough.setOnClickListener {
            startDoctorActivity("General Physician")
        }
        cdFever.setOnClickListener {
            startDoctorActivity("General Physician")
        }
        cdStomach.setOnClickListener {
            startDoctorActivity("General Physician")
        }
        cdHead.setOnClickListener {
            startDoctorActivity("General Physician")
        }
        cdWeight.setOnClickListener {
            startDoctorActivity("General Physician")
        }

    }
    private fun startDoctorActivity(category: String) {
        val intent = Intent(this, DoctorActivity::class.java)
        intent.putExtra("category", category)
        startActivity(intent)
    }
}