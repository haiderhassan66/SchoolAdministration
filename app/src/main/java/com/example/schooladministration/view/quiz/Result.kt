package com.example.schooladministration.view.quiz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.schooladministration.R
import com.example.schooladministration.databinding.FragmentResultBinding

class Result : Fragment() {

    private var _binding:FragmentResultBinding? = null
    private val binding get() = _binding!!
    private val args:ResultArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        setView()
        return binding.root
    }

    private fun setView() {
        binding.textView3.text = "Your score is: ${args.result}"
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}