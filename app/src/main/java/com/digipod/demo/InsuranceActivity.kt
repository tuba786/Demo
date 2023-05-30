package com.digipod.demo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class InsuranceActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var adapter: InsuranceAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var btn: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insurance)
        db = Firebase.firestore
        auth = Firebase.auth

        recyclerView = findViewById(R.id.insuranceRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        btn = findViewById<ImageView>(R.id.goToCart)


        getInsurance()
        checkItemsInCart()
    }

    private fun getInsurance() {
        val insurances = ArrayList<InsuranceModel>()
        db.collection("insurance").get().addOnSuccessListener { querySnapshot ->
            for (document in querySnapshot) {
                val l = document.toObject(InsuranceModel::class.java)
                insurances.add(l)
            }
            adapter = InsuranceAdapter(insurances)
            recyclerView.adapter = adapter
        }
    }

    private fun checkItemsInCart() {
//        val totalCartItems =
//            db.collection("carts").document(auth.currentUser!!.uid).collection("items")
//        btn.text = "buy $totalCartItems} items"
        btn.setOnClickListener {
            val i = Intent(this, InsuranceCartActivity::class.java)
            startActivity(i)
        }
    }
}