package com.example.schooladministration.view.userlist

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
import com.example.schooladministration.databinding.FragmentUsersBinding
import com.example.schooladministration.utils.Singleton
import com.example.schooladministration.viewmodel.UserAdapterNav
import com.example.schooladministration.viewmodel.UsersViewModel
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Users : Fragment() {
    private var _binding:FragmentUsersBinding?=null
    private val binding get() = _binding!!
    private val viewModel:UsersViewModel by viewModels()
    private val args:UsersArgs by navArgs()
    private lateinit var adapter:UsersAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentUsersBinding.inflate(inflater, container, false)

        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        observer()
    }

    private fun setAdapter() {
        adapter = UsersAdapter {
            Log.d("checking",it.toString())
            findNavController().navigate(UsersDirections.actionUsersToAppointment(
                Gson().toJson(it)
            ))
        }
    }

    private fun observer() {
        viewModel.navigate.observe(viewLifecycleOwner){
            when(it){
                UserAdapterNav.ADAPTER->{
                    binding.recyclerView.adapter = adapter
                    when(Singleton.type){
                        "student"->{
                            viewModel.getUserWithEducation("consultant",Singleton.user!!.education)
                        }
                        "admin"->{
                            if (args.type=="consultant"){
                                viewModel.getUser("consultant")
                            } else {
                                viewModel.getUser("student")
                            }
                        }
                    }
//                    viewModel.getUser(Singleton.type.toString())
                }
            }
        }

        viewModel.reponse.observe(viewLifecycleOwner){
            if (it.status){
                if (it.dataArray!!.isNotEmpty()) {
                    viewModel.isListEmpty.set(false)
                    adapter.updateList(it.dataArray)
                    Log.d("checking",it.dataArray.toString())
                } else {
//                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}