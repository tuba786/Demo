package com.digipod.demo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class DocAdapter : RecyclerView.Adapter<DocAdapter.DocViewHolder>() {

    private var docs: List<DocModel> = emptyList()

    fun submitList(docs: List<DocModel>) {
        this.docs = docs
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DocViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_doc, parent, false)
        return DocViewHolder(view)
    }

    override fun onBindViewHolder(holder: DocAdapter.DocViewHolder, position: Int) {
        val doc = docs[position]
        holder.bind(doc)
    }

    override fun getItemCount(): Int {
        return docs.size
    }

    inner class DocViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val doctorNameView: TextView = itemView.findViewById(R.id.tvDocName)
        private val categoryView: TextView = itemView.findViewById(R.id.tvBranch)
        private val doctorView: ImageView = itemView.findViewById(R.id.ivDoctorImageView)
        private val placeView: TextView = itemView.findViewById(R.id.tvPlace)
        private val expView: TextView  = itemView.findViewById(R.id.tvExp)
        private val levelView: TextView = itemView.findViewById(R.id.tvLevel)
        private val langView: TextView  = itemView.findViewById(R.id.tvLang)



        fun bind(doctor: DocModel) {
            Glide.with(itemView)
                .load(doctor.docResourceId)
                .into(doctorView)
            doctorNameView.text = doctor.docName
            categoryView.text = doctor.branch
            placeView.text = doctor.place
            expView.text = doctor.exp
            levelView.text = doctor.level
            langView.text = doctor.lang


        }
    }
}
