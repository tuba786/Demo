package com.digipod.demo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MedicineActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var adapter: MedicineAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var btn: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medicine)
        db = Firebase.firestore
        auth = Firebase.auth

        recyclerView = findViewById(R.id.medicineRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        btn = findViewById<ImageView>(R.id.goToCart)


        getMedicines()
        checkItemsInCart()
    }

    private fun getMedicines() {
        val medicines = ArrayList<MedicineModel>()
        db.collection("medicine").get().addOnSuccessListener { querySnapshot ->
            for (document in querySnapshot) {
                val m = document.toObject(MedicineModel::class.java)
                medicines.add(m)
            }
            adapter = MedicineAdapter (object : MedicineAdapter.OnItemClickListener {
            override fun onItemClick(medicine: MedicineModel) {
                // Create an intent to navigate to the CartActivity and pass the selected medicine
                val intent = Intent(this@MedicineActivity, CartActivity::class.java)
                intent.putExtra("medicine", medicine)
                startActivity(intent)
            }
        },
            medicines,
            )
            recyclerView.adapter = adapter
        }
    }

    private fun checkItemsInCart() {
//        val totalCartItems =
//            db.collection("carts").document(auth.currentUser!!.uid).collection("items")
//        btn.text = "buy $totalCartItems} items"
        btn.setOnClickListener {
            val i = Intent(this, CartActivity::class.java)
            startActivity(i)
        }
    }
}


