package com.digipod.demo

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.SurfaceView
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.digipod.demo.databinding.ActivityInstantConsultationBinding
import io.agora.rtc2.IRtcEngineEventHandler
import io.agora.rtc2.RtcEngine
import io.agora.rtc2.video.VideoCanvas
import io.agora.rtc2.video.VideoEncoderConfiguration
import kotlinx.android.synthetic.main.activity_appointment.*
import kotlinx.android.synthetic.main.activity_instant_consultation.*

class InstantConsultation : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_instant_consultation)
        genPhy.setOnClickListener {
            startDoctorListActivity("General Physician")
        }
        skin.setOnClickListener {
            startDoctorListActivity("Skin and Hair")
        }
        womenHealth.setOnClickListener {
            startDoctorListActivity("Women's Health")
        }

    }
    private fun startDoctorListActivity(category: String) {
        val intent = Intent(this, DoctorListActivity::class.java)
        intent.putExtra("category", category)
        startActivity(intent)
    }
}