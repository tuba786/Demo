package com.digipod.demo


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.digipod.demo.Model.LabCartModel
import com.digipod.demo.Model.LabItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class LabTestAdapter(
    private val labList: ArrayList<LabTestModel>
) : RecyclerView.Adapter<LabTestAdapter.LabTestViewHolder>() {

    private var auth: FirebaseAuth = Firebase.auth
    private var db: FirebaseFirestore = Firebase.firestore

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LabTestAdapter.LabTestViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_lab_tests, parent, false)
        return LabTestViewHolder(view)
    }

    override fun onBindViewHolder(holder: LabTestViewHolder, position: Int) {
        val lab = labList[position]
        holder.bind(lab)
    }

    override fun getItemCount(): Int {
        return labList.size
    }

    inner class LabTestViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val labName: TextView = itemView.findViewById(R.id.tvLabTestName)
        private val labPrice: TextView = itemView.findViewById(R.id.tvLabTestPrice)
        private val labDes: TextView = itemView.findViewById(R.id.tvLabTestDes)
        private val cartBtn: TextView = itemView.findViewById(R.id.addToCartButton)
        fun bind(lab: LabTestModel) {

            labName.text = lab.name
            labPrice.text = lab.price
            labDes.text= lab.description
            cartBtn.setOnClickListener {
                val labTestPrice = lab.price.toInt() * 1
                val cart = LabCartModel(
                    auth.currentUser!!.uid,
                    LabItem(lab, labTestPrice),
                    auth.currentUser!!.phoneNumber!!
                )
                db.collection("labCarts")
                    .document(auth.currentUser!!.uid)
                    .collection("tests")
                    .add(cart)
                    .addOnSuccessListener {
                        itemView.isEnabled = false
                        itemView.tag = it.id
                    }
            }
        }
    }
}