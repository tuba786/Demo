package com.digipod.demo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.digipod.demo.databinding.DiseaseCardViewBinding
import com.digipod.demo.network.Disease

class DiseaseAdapter(
    val click: (Disease) -> Unit
) : ListAdapter<Disease, DiseaseAdapter.DiseaseViewHolder>(DiseaseDiffCallback()) {
    class DiseaseViewHolder(
        private val binding: DiseaseCardViewBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(disease: Disease) {
            binding.disease = disease
            binding.executePendingBindings()
        }
    }

    class DiseaseDiffCallback : DiffUtil.ItemCallback<Disease>() {
        override fun areItemsTheSame(oldItem: Disease, newItem: Disease): Boolean {
            return oldItem.label== newItem.label

        }

        override fun areContentsTheSame(oldItem: Disease, newItem: Disease): Boolean {
            return oldItem.score == newItem.score
        }
    }

     override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiseaseViewHolder {
        return DiseaseViewHolder(
            DiseaseCardViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DiseaseViewHolder, position: Int) {
        val disease = getItem(position)
        holder.bind(disease)
        holder.itemView.setOnClickListener { click(disease) }
    }
}