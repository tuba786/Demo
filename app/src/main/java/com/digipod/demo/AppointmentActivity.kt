package com.digipod.demo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_appointment.*


class AppointmentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_appointment)
        cdGenPhy.setOnClickListener {
            startDoctorActivity("General Physician")
        }
        cdSkin.setOnClickListener {
            startDoctorActivity("Skin and Hair")
        }
        cdWomenHealth.setOnClickListener {
            startDoctorActivity("Women's Health")
        }

    }
    private fun startDoctorActivity(category: String) {
        val intent = Intent(this, DoctorActivity::class.java)
        intent.putExtra("category", category)
        startActivity(intent)
    }
}