package com.digipod.demo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.digipod.demo.Model.InsuranceCartModel
import com.digipod.demo.Model.LabCartModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class InsuranceCartActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: InsuranceCartAdapter
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var checkout: Button
    private var amt: Float = 0f
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insurance_cart)
        db = Firebase.firestore
        auth = Firebase.auth
        recyclerView = findViewById(R.id.cartRecyclerView)
        checkout = findViewById(R.id.checkoutButton)
        recyclerView.layoutManager = LinearLayoutManager(this)
        getCartData()
        checkout.setOnClickListener {
            val i = Intent(this, CheckoutActivity::class.java)
            i.putExtra("amount", amt)
            i.putExtra("purchase", true)
            startActivity(i)
        }
    }

    private fun getCartData() {
        val insurances = ArrayList<InsuranceCartModel>()
        db.collection("insuranceCarts").document(auth.currentUser!!.uid).collection("plans").get()
            .addOnSuccessListener { snaps ->
                for (document in snaps) {
                    val p = document.toObject(InsuranceCartModel::class.java)
                    insurances.add(p)
                    Log.d("tag", p.toString())
                    amt += p.insuranceItem.price
                    Log.d("price", amt.toString())
                    checkout.text = "Checkout Rs.${amt}"
                }
                adapter = InsuranceCartAdapter(insurances)
                recyclerView.adapter = adapter
            }
    }
}
