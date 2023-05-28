package com.digipod.demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class DoctorActivity : AppCompatActivity() {
    private lateinit var recyclerViewDoctor: RecyclerView
    private lateinit var doctorAdapter: DoctorAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor)
        recyclerViewDoctor = findViewById(R.id.recyclerViewDoctor)
        recyclerViewDoctor.layoutManager = LinearLayoutManager(this)
        doctorAdapter = DoctorAdapter()

        val category = intent.getStringExtra("category")
        val doctorList = getDoctorList(category)

        doctorAdapter.submitList(doctorList)
        recyclerViewDoctor.adapter = doctorAdapter
    }
    private fun getDoctorList(category: String?): List<DoctorModel> {
        return when (category) {
            "General Physician" -> {
                listOf(
                    DoctorModel(
                        R.drawable.gp1,
                        "Lucknow",
                        "Mausam Singh",
                        "MBBS(Intern)",
                        "General Physician",
                        "English,Hindi",
                        "2 years experience",
                        1000.0f
                    ),
                    DoctorModel(
                        R.drawable.gp2,
                        "Lucknow",
                        "Llyod Dcosta",
                        "MBBS,DNB(General Medicine)",
                        "General Physician",
                        "English",
                        "7 years experience",
                        1200.0f
                    ),
                    DoctorModel(
                        R.drawable.sh1,
                        "Lucknow",
                        "Prabhakar Kumar",
                        "MBBS(Intern)",
                        "Dermatologist",
                        "Hindi,English",
                        "1.5 years experience",
                        850.0f
                    ),
                    DoctorModel(
                        R.drawable.sh2,
                        "Lucknow",
                        "Nupur Dev",
                        "MBBS,DNB(General Medicine)",
                        "Dermatologist",
                        "English",
                        "10 years experience",
                        900.0f
                    )
                )

            }
            "Skin and Hair" -> {
                listOf(
                    DoctorModel(
                        R.drawable.sh1,
                        "Lucknow",
                        "Prabhakar Kumar",
                        "MBBS(Intern)",
                        "Dermatologist",
                        "Hindi,English",
                        "1.5 years experience",
                        850.0f
                    ),
                    DoctorModel(
                        R.drawable.sh2,
                        "Lucknow",
                        "Nupur Dev",
                        "MBBS,DNB(General Medicine)",
                        "Dermatologist",
                        "English",
                        "10 years experience",
                        900.0f
                    )
                )
            }
            "Women's Health" -> {
                listOf(
                    DoctorModel(
                        R.drawable.wh1,
                        "Lucknow",
                        "Nishant Singh",
                        "MBBS(Intern)",
                        "Obstetrician",
                        "English,Hindi",
                        "1.5 years experience",
                        1000.0f
                    ),
                    DoctorModel(
                        R.drawable.wh2,
                        "Lucknow",
                        "Siya Tandon",
                        "MBBS",
                        "Gynecologist ",
                        "English",
                        "4 years experience",
                        1000.0f
                    )

                )
            }
            else -> emptyList()
        }
    }
}