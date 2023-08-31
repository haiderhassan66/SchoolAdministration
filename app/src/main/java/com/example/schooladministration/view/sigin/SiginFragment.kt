package com.example.schooladministration.view.sigin

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.schooladministration.R
import com.example.schooladministration.databinding.FragmentSiginBinding
import com.example.schooladministration.viewmodel.SignInNav
import com.example.schooladministration.viewmodel.SignInViewModel
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SiginFragment : Fragment() {
    private var _binding:FragmentSiginBinding?=null
    private val binding get() = _binding!!
    private val viewModel by viewModels<SignInViewModel>()
    private val args:SiginFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSiginBinding.inflate(layoutInflater, container, false)

//        Log.d("checking",args.type)
        when(args.type){
            "admin"->{
                binding.userIv.setImageDrawable(resources.getDrawable(R.drawable.admin))
            }
            "consultant"->{
                binding.userIv.setImageDrawable(resources.getDrawable(R.drawable.teacher))
            }
            "student"->{
                binding.userIv.setImageDrawable(resources.getDrawable(R.drawable.student))
            }
        }

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
                SignInNav.SIGNIN->{
                    viewModel.signin(args.type)
                }
                SignInNav.SIGNUP->{
                    findNavController().navigate(SiginFragmentDirections.actionSiginFragmentToSignUp(
                        args.type
                    ))
                }
            }
        }

        viewModel.signinResponse.observe(viewLifecycleOwner){
            if (it.status){
                viewModel.saveData(Gson().toJson(it.data),args.type)
//                Log.d("checking",it.data.toString())
                findNavController().navigate(SiginFragmentDirections.actionSiginFragmentToHomeScreen(
                    args.type
                ))
//                Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        viewModel.navigate.value = null
    }
}