package com.example.schooladministration.view.choosetype

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.schooladministration.databinding.FragmentChooseTypeBinding
import com.example.schooladministration.utils.Singleton
import com.example.schooladministration.viewmodel.ChooseEnum
import com.example.schooladministration.viewmodel.ChooseTypeViewModel
import dagger.hilt.android.AndroidEntryPoint

class ChooseType : Fragment() {

    private var _binding:FragmentChooseTypeBinding?=null
    private val binding get() = _binding!!
    private val viewModel by viewModels<ChooseTypeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding =  FragmentChooseTypeBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel

        if (Singleton.user!=null){
//            Log.d("checking","is login ${viewModel.isLogin}  ${viewModel.user} ${viewModel.type}")
            findNavController().navigate(ChooseTypeDirections.actionChooseTypeToHomeScreen(
                Singleton.type!!
            ))
        }
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
                    findNavController().navigate(ChooseTypeDirections.actionChooseTypeToSiginFragment(
                        "consultant"
                    ))
                }
                ChooseEnum.STUDENT->{
                    findNavController().navigate(ChooseTypeDirections.actionChooseTypeToSiginFragment(
                        "student"
                    ))
                }
                ChooseEnum.ADMIN->{
                    findNavController().navigate(ChooseTypeDirections.actionChooseTypeToSiginFragment(
                        "admin"
                    ))
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        viewModel.Navigate.value = null
    }
}