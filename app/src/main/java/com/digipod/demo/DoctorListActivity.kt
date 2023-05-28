package com.digipod.demo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_doctor.*

class DoctorListActivity : AppCompatActivity() {
    private lateinit var recyclerViewDoc: RecyclerView
    private lateinit var docAdapter: DocAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_list)
        recyclerViewDoc = findViewById(R.id.recyclerViewDoc)
        recyclerViewDoc.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        docAdapter = DocAdapter()

        val category = intent.getStringExtra("category")
        val docList = getDocList(category)

        docAdapter.submitList(docList)
        recyclerViewDoc.adapter = docAdapter
        val btn = findViewById<Button>(R.id.btnPay)
        btn.setOnClickListener {
            Intent(this, CheckoutActivity::class.java).also {
                it.putExtra("amount", 240.00f)
                it.putExtra("instant", "instant")
                startActivity(it)
            }
        }
    }

    private fun getDocList(category: String?): List<DocModel> {
        return when (category) {
            "General Physician" -> {
                listOf(
                    DocModel(
                        R.drawable.gp1,
                        "Lucknow",
                        "Mausam Singh",
                        "MBBS(Intern)",
                        "General Physician",
                        "English,Hindi",
                        "2 years experience",

                        ),
                    DocModel(
                        R.drawable.gp2,
                        "Lucknow",
                        "Llyod Dcosta",
                        "MBBS,DNB(General Medicine)",
                        "General Physician",
                        "English",
                        "7 years experience",

                        )
                )
            }
            "Skin and Hair" -> {
                listOf(
                    DocModel(
                        R.drawable.sh1,
                        "Lucknow",
                        "Prabhakar Kumar",
                        "MBBS(Intern)",
                        "Dermatologist",
                        "Hindi,English",
                        "1.5 years experience",

                        ),
                    DocModel(
                        R.drawable.sh2,
                        "Lucknow",
                        "Nupur Dev",
                        "MBBS,DNB(General Medicine)",
                        "Dermatologist",
                        "English",
                        "10 years experience",

                        )
                )
            }
            "Women's Health" -> {
                listOf(
                    DocModel(
                        R.drawable.wh1,
                        "Lucknow",
                        "Nishant Singh",
                        "MBBS(Intern)",
                        "Obstetrician",
                        "English,Hindi",
                        "1.5 years experience",

                        ),
                    DocModel(
                        R.drawable.wh2,
                        "Lucknow",
                        "Siya Tandon",
                        "MBBS",
                        "Gynecologist ",
                        "English",
                        "4 years experience",

                        )

                )
            }
            else -> emptyList()
        }
    }
}
