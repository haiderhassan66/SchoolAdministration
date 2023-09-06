package com.example.schooladministration.view.signUp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.schooladministration.R
import com.example.schooladministration.databinding.FragmentSignUpBinding
import com.example.schooladministration.utils.Singleton
import com.example.schooladministration.utils.Util
import com.example.schooladministration.viewmodel.SignInNav
import com.example.schooladministration.viewmodel.SignUpViewModel
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUp : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<SignUpViewModel>()
    private val args: SignUpArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)

        binding.viewModel = viewModel

        val list = resources.getStringArray(R.array.education)
        viewModel.spinnerItems.value = list.toMutableList()

        viewModel.type.value = args.type
        binding.levelSpinner.adapter = ArrayAdapter(
            requireContext(),
            androidx.transition.R.layout.support_simple_spinner_dropdown_item,
            list
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observer()

    }

    private fun observer() {
        viewModel.signUpNav.observe(viewLifecycleOwner) {
            when (it) {
                SignInNav.SIGNUP -> {
                    Util.show_loader(requireContext())
                    viewModel.signUp()
//                    Firebase.signUp(viewModel.emailText.value.toString(),viewModel.passwordText.value.toString(),
//                    viewModel.education,viewModel.nameText.value.toString(),args.type){isTrue,message->
//                        if (isTrue){
//                            Log.d("checking","user added")
//                            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
//                        } else {
//                            Log.d("checking",message)
//                            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
//                        }
//                    }
                }

                else -> {

                }
            }
        }

        viewModel.reponse.observe(viewLifecycleOwner) {
            Util.hide_loader()
            if (it.status) {
                Singleton.user = it.data
                Singleton.type = args.type
                viewModel.saveData(Gson().toJson(it.data), args.type)

                findNavController().navigate(SignUpDirections.actionSignUpToHomeScreen(args.type))

            } else {
//                Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        viewModel.signUpNav.value = null
    }
}