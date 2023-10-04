package com.example.schooladministration.view.quiz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.schooladministration.R
import com.example.schooladministration.databinding.FragmentPaperBinding
import com.example.schooladministration.databinding.FragmentSelectSubjectBinding
import com.example.schooladministration.utils.Singleton
import com.example.schooladministration.view.paper.PaperAdapter
import com.example.schooladministration.view.paper.PaperDirections

class SelectSubject : Fragment() {
    private var _binding: FragmentSelectSubjectBinding?=null
    private val binding get() = _binding!!
    private lateinit var adapter: PaperAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSelectSubjectBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
    }

    private fun setAdapter() {
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
        adapter = PaperAdapter(list){
            if (Singleton.type == "student"){
                findNavController().navigate(R.id.action_selectSubject_to_quiz)
            } else {
                findNavController().navigate(R.id.action_selectSubject_to_questions)
            }
        }
        binding.recyclerView.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}