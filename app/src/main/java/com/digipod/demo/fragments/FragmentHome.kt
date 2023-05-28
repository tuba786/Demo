package com.digipod.demo.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.digipod.demo.*
import com.digipod.demo.databinding.FragmentHomeBinding
import kotlinx.android.synthetic.main.fragment_home.*


class FragmentHome : Fragment() {
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root

    }

    @SuppressLint("Range")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.cvBookAppointment.setOnClickListener {
            val intent=Intent(context, AppointmentActivity::class.java)
            startActivity(intent)
        }
        binding.cvOrderMedicine.setOnClickListener {
            val intent=Intent(context, MedicineActivity::class.java)
            startActivity(intent)
        }
        binding.cdHospitals.setOnClickListener {
            val intent=Intent(context, HospitalActivity::class.java)
            startActivity(intent)
        }
        binding.cdPharm.setOnClickListener {
            val intent=Intent(context, PharmacyActivity::class.java)
            startActivity(intent)
        }
        binding.cdDiet.setOnClickListener {
            val intent=Intent(context, DietRecommendation::class.java)
            startActivity(intent)
        }
        binding.instantConsultation.setOnClickListener {
//            startActivity(Intent(activity, InstantConsultation::class.java))
            // val dialIntent = Intent(Intent.ACTION_DIAL)
            // dialIntent.data = Uri.parse("tel:" + et_whatsapp.text.toString())
            // startActivity(dialIntent)
            Intent(requireContext(), InstantConsultation::class.java).also{
                requireActivity().startActivity(it)
            }
        }
        video1.setOnClickListener {
            openVideo("https://youtu.be/XerQiH1AHY4")
        }
        video2.setOnClickListener {
            openVideo("https://youtu.be/PMzvbztSFI8")
        }
        video3.setOnClickListener {
            openVideo("https://youtu.be/Gcz0tB_a4dY")
        }
        video4.setOnClickListener {
            openVideo("https://youtu.be/dqulL1ALdqg")
        }
        video5.setOnClickListener {
            openVideo("https://youtu.be/Gz-wLcSFDh0")
        }
        video6.setOnClickListener {
            openVideo("https://youtu.be/8lEpvr4Pe-Q")
        }

    }

    private fun openVideo(url: String) {
        val uri = Uri.parse(url)
        startActivity(Intent(Intent.ACTION_VIEW, uri))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
