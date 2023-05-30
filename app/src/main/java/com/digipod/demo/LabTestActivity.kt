package com.digipod.demo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.checkerframework.checker.units.qual.m

class LabTestActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var adapter: LabTestAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var btn: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lab_test)
        db = Firebase.firestore
        auth = Firebase.auth

        recyclerView = findViewById(R.id.labRecyclerView)
        val gridLayoutManager = GridLayoutManager(this, 2)
        recyclerView.layoutManager = gridLayoutManager
        btn = findViewById<ImageView>(R.id.goToCart)


        getLabTests()
        checkItemsInCart()
    }

    private fun getLabTests() {
        val labs = ArrayList<LabTestModel>()
        db.collection("lab").get().addOnSuccessListener { querySnapshot ->
            for (document in querySnapshot) {
                val l = document.toObject(LabTestModel::class.java)
                labs.add(l)
            }
            adapter = LabTestAdapter(labs)
            recyclerView.adapter = adapter
        }
    }

    private fun checkItemsInCart() {
//        val totalCartItems =
//            db.collection("carts").document(auth.currentUser!!.uid).collection("items")
//        btn.text = "buy $totalCartItems} items"
        btn.setOnClickListener {
            val i = Intent(this, LabCartActivity::class.java)
            startActivity(i)
        }
    }
}