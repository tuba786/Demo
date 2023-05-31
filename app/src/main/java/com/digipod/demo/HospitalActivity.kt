package com.digipod.demo

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.digipod.demo.fragments.FragmentHome
import kotlinx.android.synthetic.main.activity_appointment.*

class HospitalActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var hospitalAdapter: HospitalAdapter
    private var hospitalList = ArrayList<HospitalModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hospital)
        recyclerView = findViewById(R.id.hospitalRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        hospitalAdapter = HospitalAdapter(hospitalList)
        recyclerView.adapter = hospitalAdapter
        buildHospitalList()
        btnBack.setOnClickListener {
            val intent = Intent(this, FragmentHome::class.java)
            startActivity(intent)
        }
    }

    private fun buildHospitalList() {
        hospitalList.add(
            HospitalModel(
                R.drawable.hospital,
                "Pristyn Care - Lucknow",
                "B -4/216 Vishal Khand 4 Gomti Nagar , Lucknow - "
            )
        )
        hospitalList.add(
            HospitalModel(
                R.drawable.hospital,
                "Garg OpthalMic",
                "B-54, Bans Mandi, Nirala Nagar , Lucknow - 226020"
            )
        )
        hospitalList.add(
            HospitalModel(
                R.drawable.hospital,
                "Vaga Hospital",
                "KS-14, Aligunj Housing Scheme Sitapur Road , Lucknow"
            )
        )
        hospitalList.add(
            HospitalModel(
                R.drawable.hospital,
                "Manju Hospital",
                "14, 15, Sector 14, Indira Nagar, Lucknow, Uttar Pradesh 226016 , Lucknow"
            )
        )
        hospitalList.add(
            HospitalModel(
                R.drawable.hospital,
                "Sahara Hospital",
                "Viraj Khand, Near Haniman Chauraha Gomti Nagar, Lucknow - 226010"
            )
        )
        hospitalList.add(
            HospitalModel(
                R.drawable.hospital,
                "SIPS Super Speciality Hospital",
                "1203, Jawahar Nagar, Qaisar Bagh, Lucknow, Uttar Pradesh 226018, India , Lucknow"
            )
        )
        hospitalList.add(
            HospitalModel(
                R.drawable.hospital,
                "Ford Hospital",
                "Unit Of Nova Hospital, Vikas Khand- 1, Patrakarpuram Crossing Gomti Nagar, Lucknow - 226010"
            )
        )
        hospitalList.add(
            HospitalModel(
                R.drawable.hospital,
                "Neera Hospital",
                "B-2, Mahanagar Extension, Near Aliganj Crossing Mahanagar, Lucknow - 226006"
            )
        )
        hospitalAdapter.notifyDataSetChanged()
    }
}

