package com.digipod.demo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.digipod.demo.Model.MedicineCartModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CartActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CartAdapter
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var checkout: Button
    private var amt: Float = 0f
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
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
        val medicines = ArrayList<MedicineCartModel>()
        db.collection("carts").document(auth.currentUser!!.uid).collection("items").get()
            .addOnSuccessListener { snaps ->
                for (document in snaps) {
                    val m = document.toObject(MedicineCartModel::class.java)
                    medicines.add(m)
                    Log.d("tag", m.toString())
                    amt += m.medicineItem.price
                    Log.d("price", amt.toString())
                    checkout.text = "Checkout Rs.${amt}"
                }
                adapter = CartAdapter(medicines)
                recyclerView.adapter = adapter
            }
    }
}
