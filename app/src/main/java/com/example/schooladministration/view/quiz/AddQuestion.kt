package com.example.schooladministration.view.quiz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.schooladministration.R
import com.example.schooladministration.databinding.FragmentAddQuestionBinding
import com.example.schooladministration.utils.Util
import com.example.schooladministration.viewmodel.AddQuestionViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddQuestion : Fragment() {

    private var _binding:FragmentAddQuestionBinding? = null
    private val binding get() = _binding!!
    private val viewModel:AddQuestionViewModel by viewModels()
    private val args:AddQuestionArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddQuestionBinding.inflate(inflater, container, false)
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
                AddQuestionViewModel.Navigate.ERROR->{
                    Toast.makeText(requireContext(), "All fields required!", Toast.LENGTH_SHORT).show()
                }
                AddQuestionViewModel.Navigate.DONE->{
                    Util.show_loader(requireContext())
                    viewModel.addQuestion(args.subject)
                }
            }
        }
        viewModel.response.observe(viewLifecycleOwner){
            Util.hide_loader()
            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}