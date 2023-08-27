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
import com.example.schooladministration.utils.Firebase
import com.example.schooladministration.viewmodel.SignInNav
import com.example.schooladministration.viewmodel.SignInViewModel

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

        Log.d("checking",args.type)
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
                    Firebase.signIn(viewModel.emailText.value.toString(),viewModel.passwordText.value.toString(),args.type){isTrue,message,model->
                        if (isTrue){
                            Log.d("checking",model.toString())
                            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                        } else {
                            Log.d("checking",message)
                            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                SignInNav.SIGNUP->{
                    findNavController().navigate(SiginFragmentDirections.actionSiginFragmentToSignUp(
                        args.type
                    ))
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        viewModel.navigate.value = null
    }
}