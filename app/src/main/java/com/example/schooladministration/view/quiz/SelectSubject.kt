package com.example.schooladministration.view.quiz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.schooladministration.R
import com.example.schooladministration.databinding.FragmentSelectSubjectBinding
import com.example.schooladministration.utils.Singleton
import com.example.schooladministration.view.paper.SubjectAdapter

class SelectSubject : Fragment() {
    private var _binding: FragmentSelectSubjectBinding?=null
    private val binding get() = _binding!!
    private lateinit var adapter: SubjectAdapter

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
        adapter = SubjectAdapter(list){
            if (Singleton.type == "student"){
                findNavController().navigate(SelectSubjectDirections.actionSelectSubjectToQuiz(
                    it
                ))
            } else {
                findNavController().navigate(SelectSubjectDirections.actionSelectSubjectToQuestions(
                    it
                ))
            }
        }
        binding.recyclerView.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}