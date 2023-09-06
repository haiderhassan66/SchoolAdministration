package com.example.schooladministration.view.paper.paperdetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.example.schooladministration.R
import com.example.schooladministration.databinding.FragmentPaperDetailsBinding
import com.example.schooladministration.viewmodel.PaperDetailsViewModel

class PaperDetails : Fragment() {
    private var _binding:FragmentPaperDetailsBinding?=null
    private val binding get() = _binding!!
    private val viewModel:PaperDetailsViewModel by viewModels()
    private val args:PaperDetailsArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPaperDetailsBinding.inflate(inflater, container, false)

        binding.viewModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (args.id == 0){
            binding.viewModel!!.picObservable.set(true)
            binding.viewModel!!.textObservable.set(false)
        } else {
            binding.viewModel!!.picObservable.set(false)
            binding.viewModel!!.textObservable.set(true)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}