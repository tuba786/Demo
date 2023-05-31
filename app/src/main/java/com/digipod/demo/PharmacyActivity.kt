package com.digipod.demo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.digipod.demo.fragments.FragmentHome
import kotlinx.android.synthetic.main.activity_appointment.*

class PharmacyActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var pharmAdapter: PharmacyAdapter
    private var pharmList = arrayListOf<PharmacyModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pharmacy)
        recyclerView = findViewById(R.id.pharmRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        pharmAdapter = PharmacyAdapter(pharmList)
        recyclerView.adapter = pharmAdapter
        btnBack.setOnClickListener {
            val intent = Intent(this, FragmentHome::class.java)
            startActivity(intent)
        }
    buildPharmacyList()
    }
    private fun buildPharmacyList(){
        pharmList.add(
            PharmacyModel(R.drawable.pharm,
            "Bhagwan Medicals",
                "Aminabad, Lucknow, 226017"
            )
        )
        pharmList.add(PharmacyModel(R.drawable.pharm,
            "Nidhish Medicals",
            "Indira Nagar, Lucknow, 226016"))
        pharmList.add(PharmacyModel(R.drawable.pharm,
            "Raj Medicals",
            "Chowk, Lucknow, 226003"))
        pharmList.add(PharmacyModel(R.drawable.pharm,
            "Apollo Pharmacy",
            "Aliganj, Lucknow, 226024"))
        pharmList.add(PharmacyModel(R.drawable.pharm,
            "Mohan Medical Store",
            "Mahanagar, Lucknow, 226006"))
        pharmList.add(PharmacyModel(R.drawable.pharm,
            "Super Drug Store",
            "Gomti Nagar, Lucknow, 226010"))
        pharmList.add(PharmacyModel(R.drawable.pharm,
            "Saif Medical Store",
            "Hussainabad, Lucknow, 226003"))
        pharmList.add(PharmacyModel(R.drawable.pharm,
            "City Pharmacy",
            "Daulatganj, Lucknow, 226003"))

        pharmAdapter.notifyDataSetChanged()
    }
}