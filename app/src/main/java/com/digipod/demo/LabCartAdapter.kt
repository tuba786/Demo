package com.digipod.demo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.digipod.demo.Model.LabCartModel

class LabCartAdapter(
    private val labCartItems: ArrayList<LabCartModel>
) : RecyclerView.Adapter<LabCartAdapter.LabCartViewHolder>() {

    inner class LabCartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val labName: TextView = itemView.findViewById(R.id.labName)
        private val labPrice: TextView = itemView.findViewById(R.id.labPrice)
        private val labDes: TextView = itemView.findViewById(R.id.labDes)

        fun bind(item: LabCartModel) {
            // Bind the medicine data to the views

            labName.text = item.labItem.lab.name
            labDes.text = item.labItem.lab.description
            labPrice.text = item.labItem.price.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LabCartAdapter.LabCartViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_lab_cart, parent, false)
        return LabCartViewHolder(view)
    }

    override fun onBindViewHolder(holder: LabCartViewHolder, position: Int) {
        val lab = labCartItems[position]
        holder.bind(lab)
    }

    override fun getItemCount(): Int {
        return labCartItems.size
    }
}
