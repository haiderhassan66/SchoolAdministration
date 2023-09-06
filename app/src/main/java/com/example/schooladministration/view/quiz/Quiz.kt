package com.example.schooladministration.view.quiz

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.schooladministration.R
import com.example.schooladministration.databinding.FragmentQuizBinding
import com.example.schooladministration.model.QuizModel
import com.example.schooladministration.utils.Util

class Quiz : Fragment() {
    private var _binding:FragmentQuizBinding?=null
    private val binding get() = _binding!!
    private lateinit var adapter: QuizPagerAdapter
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

        val list = mutableListOf<QuizModel>()

        list.apply {
            add(QuizModel
                ("Grand Central Terminal, Park Avenue, New York is the world's",
                "largest railway station",
                "highest railway station",
                "longest railway station",
                "None of the above",
                "largest railway station"))
            add(QuizModel(
                "Entomology is the science that studies",
                "Behavior of human beings",
                "Insects",
                "The origin and history of technical and scientific terms",
                "The formation of rocks",
                "Insects"))
            add(QuizModel(
                "Eritrea, which became the 182nd member of the UN in 1993, is in the continent of",
                "Asia","Africa","Europe","Australia","Africa"))
            add(QuizModel(
                "For which of the following disciplines is Nobel Prize awarded?",
                "Physics and Chemistry",
                "Physiology or Medicine",
                "Literature, Peace and Economics","All of the above","All of the above"))
            add(QuizModel(
                "Epsom (England) is the place associated with",
                "Horse racing",
                "Polo","Shooting","Snooker","Horse racing")
            )
        }

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)

                // If the user is actively dragging (scrolling), disable touch events.
                if (state == ViewPager2.SCROLL_STATE_DRAGGING) {
                    binding.viewPager.isUserInputEnabled = false
                } else if (state == ViewPager2.SCROLL_STATE_IDLE) {
                    // If the user has stopped scrolling, re-enable touch events.
                    binding.viewPager.isUserInputEnabled = true
                }
            }
        })

        binding.viewPager.adapter = QuizPagerAdapter(Util.quizList()){
            when(it){
                0->{
                    binding.viewPager.currentItem=1
                }
                1->{
                    binding.viewPager.currentItem=2
                }
                2->{
                    binding.viewPager.currentItem=3
                }
                3->{
                    binding.viewPager.currentItem=4
                }
                4->{
                    binding.viewPager.currentItem=5
                }
                5->{
                    binding.viewPager.currentItem=6
                }
            }
        }

//        binding.viewPager.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}