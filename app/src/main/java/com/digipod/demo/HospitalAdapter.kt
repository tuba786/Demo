package com.digipod.demo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class HospitalAdapter(private val hospitalList: List<HospitalModel>) :
    RecyclerView.Adapter<HospitalAdapter.HospitalViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HospitalViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_hospital, parent, false)
        return HospitalViewHolder(view)
    }

    override fun onBindViewHolder(holder: HospitalViewHolder, position: Int) {
        val hospital = hospitalList[position]
        holder.bind(hospital)
    }

    override fun getItemCount(): Int {
        return hospitalList.size
    }

    inner class HospitalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val hospitalImageView: ImageView = itemView.findViewById(R.id.hospitalImageView)
        private val hospitalNameTextView: TextView = itemView.findViewById(R.id.hospitalNameTextView)
        private val hospitalAddressTextView: TextView = itemView.findViewById(R.id.hospitalAddressTextView)

        fun bind(hospital: HospitalModel) {
            Glide.with(itemView)
                .load(hospital.hospitalImage)
                .into(hospitalImageView)
            hospitalNameTextView.text = hospital.hospitalName
            hospitalAddressTextView.text = hospital.hospitalAddress
        }
    }
}
