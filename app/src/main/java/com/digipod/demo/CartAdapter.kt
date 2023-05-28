package com.digipod.demo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.digipod.demo.Model.MedicineCartModel

class CartAdapter(
    private val cartItems: ArrayList<MedicineCartModel>
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    inner class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val medicineImageView: ImageView = itemView.findViewById(R.id.medicineImageView)
        private val medicineName: TextView = itemView.findViewById(R.id.medicineName)
        private val medicinePrice: TextView = itemView.findViewById(R.id.medicinePrice)

        fun bind(item: MedicineCartModel) {
            // Bind the medicine data to the views
            Glide.with(itemView)
                .load(item.medicineItem.medicine.image)
                .into(medicineImageView)
            medicineName.text = item.medicineItem.medicine.name
            medicinePrice.text = item.medicineItem.price.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cart, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val medicine = cartItems[position]
        holder.bind(medicine)
    }

    override fun getItemCount(): Int {
        return cartItems.size
    }
}
