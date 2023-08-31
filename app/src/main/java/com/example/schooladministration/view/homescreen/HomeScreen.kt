package com.example.schooladministration.view.homescreen

import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.schooladministration.R
import com.example.schooladministration.databinding.FragmentHomeScreenBinding
import com.example.schooladministration.viewmodel.HomeNav
import com.example.schooladministration.viewmodel.HomeViewModel

class HomeScreen : Fragment() {
    val args:HomeScreenArgs by navArgs()
    var _binding:FragmentHomeScreenBinding?=null
    val binding get() = _binding!!
    val viewModel:HomeViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeScreenBinding.inflate(inflater, container, false)

        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observer()
    }

    private fun observer() {
        viewModel.navigate.observe(viewLifecycleOwner){
            when(it){
                HomeNav.DRAWER->{
                    openDrawer()
                }
                HomeNav.STUDENT->{
                    closeDrawer()
                }
                else ->{

                }
            }
        }
    }

    private fun closeDrawer() {
        binding.drawerLayout.closeDrawer(Gravity.LEFT)
    }

    private fun openDrawer() {
        binding.drawerLayout.openDrawer(Gravity.LEFT)
    }
}