package com.digipod.demo

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.digipod.demo.Model.MedicineCartModel
import com.digipod.demo.Model.MedicineItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MedicineAdapter( private val onItemClickListener: OnItemClickListener,
    private val medicineList: ArrayList<MedicineModel>
) : RecyclerView.Adapter<MedicineAdapter.MedicineViewHolder>() {

    private var auth: FirebaseAuth = Firebase.auth
    private var db: FirebaseFirestore = Firebase.firestore

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicineViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_medicine, parent, false)
        return MedicineViewHolder(view)
    }

    override fun onBindViewHolder(holder: MedicineViewHolder, position: Int) {
        val medicine = medicineList[position]
        holder.bind(medicine)
    }

    override fun getItemCount(): Int {
        return medicineList.size
    }

    inner class MedicineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        TextWatcher {
        private val medicineImageView: ImageView = itemView.findViewById(R.id.medicineImageView)
        private val medicineName: TextView = itemView.findViewById(R.id.medicineName)
        private val medicinePrice: TextView = itemView.findViewById(R.id.medicinePrice)
        private val medicineManufacturer: TextView = itemView.findViewById(R.id.medicineManufacturer)
        private val qty: TextView = itemView.findViewById(R.id.quantityTextView)
        private val inc: ImageButton = itemView.findViewById(R.id.incrementButton)
        private val dec: ImageButton = itemView.findViewById(R.id.decrementButton)
        private val cartBtn: TextView = itemView.findViewById(R.id.addToCartButton)


        fun bind(medicine: MedicineModel) {
            qty.visibility = View.GONE
            inc.visibility = View.GONE
            dec.visibility = View.GONE
            Glide.with(itemView)
                .load(medicine.image)
                .into(medicineImageView)
            medicineName.text = medicine.name
            medicinePrice.text = medicine.price
            medicineManufacturer.text = medicine.manufacturer
            cartBtn.setOnClickListener {
                val medPrice = medicine.price.toInt() * 1
                val cart = MedicineCartModel(
                    auth.currentUser!!.uid,
                    MedicineItem(medicine, 1, medPrice),
                    auth.currentUser!!.phoneNumber!!
                )
                db.collection("carts")
                    .document(auth.currentUser!!.uid)
                    .collection("items")
                    .add(cart)
                    .addOnSuccessListener {
                        qty.visibility = View.VISIBLE
                        inc.visibility = View.VISIBLE
                        dec.visibility = View.VISIBLE
                        itemView.isEnabled = false
                        itemView.tag = it.id
                    }

            }
            inc.setOnClickListener {
                var quantity = qty.text.toString().toInt()
                quantity++
                qty.text = quantity.toString()
                val medPrice = medicine.price.toInt() * quantity
                val docID = itemView.tag.toString()
                db.collection("carts")
                    .document(auth.currentUser!!.uid)
                    .collection("items")
                    .document(docID)
                    .update("medicineItem", MedicineItem(medicine, quantity, medPrice))
            }
            dec.setOnClickListener {
                var quantity = qty.text.toString().toInt()
                quantity--
                qty.text = quantity.toString()
                val medPrice = medicine.price.toInt() * quantity
                val docID = itemView.tag.toString()
                db.collection("carts")
                    .document(auth.currentUser!!.uid)
                    .collection("items")
                    .document(docID)
                    .update("medicineItem", MedicineItem(medicine, quantity, medPrice))
            }
            qty.addTextChangedListener(this)
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun afterTextChanged(p0: Editable?) {
            val quantity = p0.toString().toInt()
            inc.isEnabled = quantity < 10
            dec.isEnabled = quantity > 0

        }
    }

    // Define the interface for the item click listener
    interface OnItemClickListener {
        fun onItemClick(medicine: MedicineModel)
    }
}


