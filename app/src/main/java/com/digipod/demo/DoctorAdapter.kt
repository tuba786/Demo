package com.digipod.demo

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog

class DoctorAdapter : RecyclerView.Adapter<DoctorAdapter.DoctorViewHolder>() {

    private var doctors: List<DoctorModel> = emptyList()

    fun submitList(doctors: List<DoctorModel>) {
        this.doctors = doctors
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_doctor, parent, false)
        return DoctorViewHolder(view)
    }

    override fun onBindViewHolder(holder: DoctorViewHolder, position: Int) {
        val doctor = doctors[position]
        holder.bind(doctor)
    }

    override fun getItemCount(): Int {
        return doctors.size
    }

    inner class DoctorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val doctorNameTextView: TextView = itemView.findViewById(R.id.tvDoctorName)
        private val categoryTextView: TextView = itemView.findViewById(R.id.tvSpecialization)
        private val doctorImageView: ImageView=itemView.findViewById(R.id.ivDoctorImage)
        private val slotButton: Button =itemView.findViewById(R.id.btnBook)


        fun bind(doctor: DoctorModel) {
            Glide.with(itemView)
                .load(doctor.doctorResourceId)
                .into(doctorImageView)
            doctorNameTextView.text = doctor.doctorName
            categoryTextView.text = doctor.specialization
            slotButton.setOnClickListener {
                showSlotSelectionDialog(it,doctor)
            }
        }
    }

    private fun showSlotSelectionDialog(itemView: View, doctor: DoctorModel) {
        val dialog = BottomSheetDialog(itemView.context)
        val content = LayoutInflater.from(itemView.context).inflate(R.layout.dialog_slot_selection, null)
        dialog.setContentView(content)
        val tvChoice1 = content.findViewById<TextView>(R.id.tvChoice1)
        val tvChoice2 = content.findViewById<TextView>(R.id.tvChoice2)
        val tvChoice3 = content.findViewById<TextView>(R.id.tvChoice3)
        val tvChoice4 = content.findViewById<TextView>(R.id.tvChoice4)
        val tvChoice5 = content.findViewById<TextView>(R.id.tvChoice5)
        val tvChoice6 = content.findViewById<TextView>(R.id.tvChoice6)
        val tvChoice7 = content.findViewById<TextView>(R.id.tvChoice7)
        val tvChoice8 = content.findViewById<TextView>(R.id.tvChoice8)
        val tvChoice9 = content.findViewById<TextView>(R.id.tvChoice9)
        val tvChoice10 = content.findViewById<TextView>(R.id.tvChoice10)
        val tvChoice11 = content.findViewById<TextView>(R.id.tvChoice11)
        tvChoice1.setOnClickListener {view->
            dialog.dismiss()
            val i = Intent(view.context, CheckoutActivity::class.java)
            val slot = tvChoice1.text.toString()
            i.putExtra("slot", slot)
            i.putExtra("amount", doctor.fees)
            view.context.startActivity(i)
        }

        dialog.show()
    }

    private fun bookAppointment(itemView: View, doctor: DoctorModel, selectedSlot: String) {
        // Access the selected doctor and slot information
        val selectedDoctorName = doctor.doctorName
        val selectedDoctorSpecialization = doctor.specialization

        // Perform any necessary actions with the selected doctor and slot information
        // For example, you can save the appointment to a database, display a confirmation message, etc.

        // Start the next activity or perform any other desired action
        val intent = Intent(itemView.context, PaymentActivity::class.java)
        intent.putExtra("selectedDoctorName", selectedDoctorName)
        intent.putExtra("selectedDoctorSpecialization", selectedDoctorSpecialization)
        intent.putExtra("selectedSlot", selectedSlot)
        itemView.context.startActivity(intent)
    }


    inner class SlotAdapter(
        private val slots: List<String>,
        private val slotClickListener: (String) -> Unit
    ) : RecyclerView.Adapter<SlotAdapter.SlotViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SlotViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_slot, parent, false)
            return SlotViewHolder(view)
        }

        override fun onBindViewHolder(holder: SlotViewHolder, position: Int) {
            val slot = slots[position]
            holder.bind(slot)
        }

        override fun getItemCount(): Int {
            return slots.size
        }

        inner class SlotViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private val slotTextView: TextView = itemView.findViewById(R.id.slotTextView)

            fun bind(slot: String) {
                slotTextView.text = slot

                itemView.setOnClickListener {
                    slotClickListener.invoke(slot)
                }
            }
        }
    }
}





