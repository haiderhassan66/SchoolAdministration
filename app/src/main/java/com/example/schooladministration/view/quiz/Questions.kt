package com.example.schooladministration.view.quiz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.schooladministration.R
import com.example.schooladministration.databinding.FragmentQuestionsBinding
import com.example.schooladministration.databinding.FragmentSelectSubjectBinding
import com.example.schooladministration.utils.Singleton
import com.example.schooladministration.utils.Util
import com.example.schooladministration.viewmodel.QuestionsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Questions : Fragment() {
    private var _binding: FragmentQuestionsBinding?=null
    private val binding get() = _binding!!
    private val viewModel:QuestionsViewModel by viewModels()
    private val args:QuestionsArgs by navArgs()
    private lateinit var adapter:QuestionAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentQuestionsBinding.inflate(inflater, container, false)

        if (Singleton.type=="student"){
            binding.add.visibility = View.GONE
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getQuestions(args.subject)
        setAdapter()
        observer()
        clickListener()
    }

    private fun setAdapter() {
        adapter = QuestionAdapter{
            Util.show_loader(requireContext())
            viewModel.deleteQuestion(it.id)
        }

        binding.recyclerView.adapter = adapter
    }

    private fun observer() {
        viewModel.questions.observe(viewLifecycleOwner){
            if (it.status){
                if (it.quizArray!!.isNotEmpty()){
                    adapter.updateList(it.quizArray)
                    binding.noQuestionTV.visibility = View.GONE
                } else {
                    adapter.updateList(it.quizArray)
                    binding.noQuestionTV.visibility = View.VISIBLE
                }
            }
        }
        viewModel.deleteQuestion.observe(viewLifecycleOwner){
            Util.hide_loader()
            if (it.status){
                viewModel.getQuestions(args.subject)
            }
        }
    }

    private fun clickListener() {
        binding.add.setOnClickListener {
            findNavController().navigate(QuestionsDirections.actionQuestionsToAddQuestion(
                args.subject
            ))
        }
    }

//    override fun onResume() {
//        super.onResume()
//        viewModel.getQuestions(args.subject)
//    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}