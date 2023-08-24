package com.example.schooladministration.view.choosetype

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.schooladministration.R
import com.example.schooladministration.databinding.FragmentChooseTypeBinding
import com.example.schooladministration.viewmodel.ChooseEnum
import com.example.schooladministration.viewmodel.ChooseTypeViewModel

class ChooseType : Fragment() {

    private var _binding:FragmentChooseTypeBinding?=null
    private val binding get() = _binding!!
    val viewModel by viewModels<ChooseTypeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding =  FragmentChooseTypeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observer()
    }

    private fun observer() {
        viewModel.Navigate.observe(viewLifecycleOwner){
            when(it){
                ChooseEnum.CONSULTANT->{
                    Toast.makeText(requireContext(), "CONSULTANT", Toast.LENGTH_SHORT).show()
                }
                ChooseEnum.STUDENT->{
                    Toast.makeText(requireContext(), "STUDENT", Toast.LENGTH_SHORT).show()
                }
                ChooseEnum.ADMIN->{
                    Toast.makeText(requireContext(), "ADMIN", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}