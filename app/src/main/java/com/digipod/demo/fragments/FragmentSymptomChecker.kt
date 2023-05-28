package com.digipod.demo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.digipod.demo.DiseaseAdapter
import com.digipod.demo.DiseaseViewModel
import com.digipod.demo.databinding.FragmentSymptomCheckerBinding


class FragmentSymptomChecker : Fragment() {
    private val viewModel: DiseaseViewModel by viewModels()
    private var _binding: FragmentSymptomCheckerBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSymptomCheckerBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.vm = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.checkButton.setOnClickListener {
            val symptoms = binding.symptomEditText.text.toString()
            if (symptoms.isNotBlank()) {
                viewModel.getResult(symptoms)
            }
        }
        viewModel.diseases.observe(viewLifecycleOwner){
            binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
           val da = DiseaseAdapter{

           }
           binding.recyclerView.adapter = da
           da.submitList(it[0])

        }
    }
}