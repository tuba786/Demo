package com.digipod.demo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.digipod.demo.InsuranceAdapter.InsuranceViewHolder
import com.digipod.demo.Model.InsuranceCartModel
import com.digipod.demo.Model.InsuranceItem
import com.digipod.demo.Model.LabCartModel
import com.digipod.demo.Model.LabItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class InsuranceAdapter(  private val insuranceList: ArrayList<InsuranceModel>
) : RecyclerView.Adapter<InsuranceViewHolder>() {

    private var auth: FirebaseAuth = Firebase.auth
    private var db: FirebaseFirestore = Firebase.firestore

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): InsuranceViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_insurance, parent, false)
        return InsuranceViewHolder(view)
    }

    override fun onBindViewHolder(holder: InsuranceViewHolder, position: Int) {
        val insurance = insuranceList[position]
        holder.bind(insurance)
    }

    override fun getItemCount(): Int {
        return insuranceList.size
    }

    inner class InsuranceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val insuranceName: TextView = itemView.findViewById(R.id.insuranceName)
        private val insurancePrice: TextView = itemView.findViewById(R.id.insurancePrice)
        private val insuranceDes: TextView = itemView.findViewById(R.id.insuranceDes)
        private val cartBtn: TextView = itemView.findViewById(R.id.addToCartButton)
        fun bind(insurance: InsuranceModel) {

            insuranceName.text = insurance.name
            insurancePrice.text = insurance.price
            insuranceDes.text= insurance.description
            cartBtn.setOnClickListener {
                val insurancePrice = insurance.price.toInt() * 1
                val cart = InsuranceCartModel(
                    auth.currentUser!!.uid,
                    InsuranceItem(insurance, insurancePrice),
                    auth.currentUser!!.phoneNumber!!
                )
                db.collection("insuranceCarts")
                    .document(auth.currentUser!!.uid)
                    .collection("plans")
                    .add(cart)
                    .addOnSuccessListener {
                        itemView.isEnabled = false
                        itemView.tag = it.id
                    }
            }
        }
    }
}