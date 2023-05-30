package com.digipod.demo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.digipod.demo.Model.InsuranceCartModel


class InsuranceCartAdapter(private val insuranceCartItems: ArrayList<InsuranceCartModel>
) : RecyclerView.Adapter<InsuranceCartAdapter.InsuranceCartViewHolder>() {

    inner class InsuranceCartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val insuranceName: TextView = itemView.findViewById(R.id.insuranceName)
        private val insurancePrice: TextView = itemView.findViewById(R.id.insurancePrice)
        private val insuranceDes: TextView = itemView.findViewById(R.id.insuranceDes)

        fun bind(item: InsuranceCartModel) {
            // Bind the medicine data to the views

            insuranceName.text = item.insuranceItem.insurance.name
            insuranceDes.text = item.insuranceItem.insurance.description
            insurancePrice.text = item.insuranceItem.price.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InsuranceCartViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_insurance_cart, parent, false)
        return InsuranceCartViewHolder(view)
    }

    override fun onBindViewHolder(holder: InsuranceCartViewHolder, position: Int) {
        val insurance = insuranceCartItems[position]
        holder.bind(insurance)
    }

    override fun getItemCount(): Int {
        return insuranceCartItems.size
    }
}
