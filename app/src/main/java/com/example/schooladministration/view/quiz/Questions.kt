package com.example.schooladministration.view.quiz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.schooladministration.R
import com.example.schooladministration.databinding.FragmentQuestionsBinding
import com.example.schooladministration.databinding.FragmentSelectSubjectBinding

class Questions : Fragment() {
    private var _binding: FragmentQuestionsBinding?=null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentQuestionsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        clickListener()
    }

    private fun clickListener() {
        binding.add.setOnClickListener {
            findNavController().navigate(R.id.action_questions_to_addQuestion)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}