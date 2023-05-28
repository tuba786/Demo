package com.digipod.demo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.digipod.demo.R

class PharmacyAdapter(private val pharmacyList: ArrayList<PharmacyModel>) :
    RecyclerView.Adapter<PharmacyAdapter.PharmacyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PharmacyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pharmacy, parent, false)
        return PharmacyViewHolder(view)
    }

    override fun onBindViewHolder(holder: PharmacyViewHolder, position: Int) {
        val pharmacy = pharmacyList[position]
        holder.bind(pharmacy)
    }

    override fun getItemCount(): Int {
        return pharmacyList.size
    }

    inner class PharmacyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val pharmacyImageView: ImageView = itemView.findViewById(R.id.pharmImageView)
        private val pharmacyNameTextView: TextView = itemView.findViewById(R.id.pharmNameTextView)
        private val pharmacyAddressTextView: TextView = itemView.findViewById(R.id.pharmAddressTextView)

        fun bind(pharmacy: PharmacyModel) {
            pharmacyImageView.setImageResource(pharmacy.pharmImage)
            pharmacyNameTextView.text = pharmacy.pharmName
            pharmacyAddressTextView.text = pharmacy.pharmAddress
        }
    }
}
