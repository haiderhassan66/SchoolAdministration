package com.example.schooladministration.view.scope

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.schooladministration.R
import com.example.schooladministration.databinding.FragmentScopeBinding
import com.example.schooladministration.utils.Singleton

class Scope : Fragment() {
    var _binding:FragmentScopeBinding?=null
    val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentScopeBinding.inflate(inflater, container, false)
//        when(Singleton.user?.education){
//            "Matriculation"->{
//                binding.scopeTv.text = getText(R.string.matric_scope)
//            }
//            "Intermediate"->{
//                binding.scopeTv.text = getText(R.string.inter_scope)
//            }
//            "Graduation"->{
//                binding.scopeTv.text = getText(R.string.graduation_scope)
//            }
//        }
        binding.scopeTv.text = getText(R.string.consulting_scope)
        return binding.root
    }
}