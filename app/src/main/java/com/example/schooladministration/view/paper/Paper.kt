package com.example.schooladministration.view.paper

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.schooladministration.databinding.FragmentPaperBinding

class Paper : Fragment() {
    private var _binding:FragmentPaperBinding?=null
    private val binding get() = _binding!!
    private lateinit var adapter: SubjectAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPaperBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val list = mutableListOf<String>()
        list.apply {
            add("Physics")
            add("Math")
            add("Urdu")
            add("English")
            add("Stats")
            add("Computer")
            add("Biology")
        }
        adapter = SubjectAdapter(list){
            findNavController().navigate(PaperDirections.actionPaperToPaperDetails(it))
        }
        binding.recyclerView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}