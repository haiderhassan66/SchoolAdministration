package com.example.schooladministration.view.quiz

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.schooladministration.R
import com.example.schooladministration.databinding.FragmentQuizBinding
import com.example.schooladministration.model.QuizModel
import com.example.schooladministration.utils.Util
import com.example.schooladministration.viewmodel.QuizViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Quiz : Fragment() {
    private var _binding:FragmentQuizBinding?=null
    private val binding get() = _binding!!
    private val viewModel: QuizViewModel by viewModels()
    private val args:QuizArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentQuizBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
//            override fun onPageScrollStateChanged(state: Int) {
//                super.onPageScrollStateChanged(state)
//
//                // If the user is actively dragging (scrolling), disable touch events.
//                if (state == ViewPager2.SCROLL_STATE_DRAGGING) {
//                    binding.viewPager.isUserInputEnabled = false
//                } else if (state == ViewPager2.SCROLL_STATE_IDLE) {
//                    // If the user has stopped scrolling, re-enable touch events.
//                    binding.viewPager.isUserInputEnabled = true
//                }
//            }
//        })
        viewModel.getQuestions(args.subject)
        observer()

//        binding.viewPager.adapter = QuizPagerAdapter(Util.quizList()){position,result->
//
//            if (position < quizList.size - 1) {
//                binding.viewPager.currentItem = position + 1
//            } else {
//                // Handle the case when there are no more questions
//                // You can show the result page or take any other action here
//            }
////            when(position){
////                0->{
////                    binding.viewPager.currentItem=1
////                }
////                1->{
////                    binding.viewPager.currentItem=2
////                }
////                2->{
////                    binding.viewPager.currentItem=3
////                }
////                3->{
////                    binding.viewPager.currentItem=4
////                }
////                4->{
////                    binding.viewPager.currentItem=5
////                }
////                5->{
////                    binding.viewPager.currentItem=6
////                }
////            }
//        }

//        binding.viewPager.adapter = adapter
    }

    private fun observer() {
        viewModel.response.observe(viewLifecycleOwner){
            if (it.status){
                if (it.quizArray!!.isNotEmpty()){
                    setAdapter(it.quizArray)
                    binding.noQuestionTV.visibility = View.GONE
                } else {
                    binding.noQuestionTV.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun setAdapter(quizList:List<QuizModel>) {
        binding.viewPager.isUserInputEnabled = false
        binding.viewPager.adapter = QuizPagerAdapter(quizList) { position, result ->

            if (position < quizList.size - 1) {
                binding.viewPager.currentItem = position + 1
            } else {
                findNavController().navigate(QuizDirections.actionQuizToResult(result))
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}