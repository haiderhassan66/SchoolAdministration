package com.example.schooladministration.view.homescreen

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.schooladministration.R
import com.example.schooladministration.databinding.FragmentHomeScreenBinding
import com.example.schooladministration.utils.Singleton
import com.example.schooladministration.utils.Util
import com.example.schooladministration.viewmodel.HomeNav
import com.example.schooladministration.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeScreen : Fragment() {
    val args:HomeScreenArgs by navArgs()
    var _binding:FragmentHomeScreenBinding?=null
    val binding get() = _binding!!
    val viewModel:HomeViewModel by viewModels()
    private lateinit var adapter: HomeScreenAdapter
    private var position:Int?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeScreenBinding.inflate(inflater, container, false)

        binding.viewModel = viewModel
        viewModel.name.set(Singleton.user?.name)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAdapter()

        when(Singleton.type){
            "consultant"->{
                binding.consultantTv.visibility = View.GONE
                binding.studentTv.visibility = View.GONE
                binding.TVAppointment.visibility = View.VISIBLE
                viewModel.consultantAppointment(Singleton.user!!.userId)
            }
            "student"->{
                binding.studentTv.visibility = View.GONE
                viewModel.studentAppointment(Singleton.user!!.userId)
            }
            "admin"->{
                binding.studentTv.visibility = View.GONE
                viewModel.getAppointment()
            }
        }

//        viewModel.getAppointment()
        observer()
    }

    private fun setAdapter() {
        adapter = HomeScreenAdapter{data,position->
            Util.show_loader(requireContext())
            viewModel.deleteAppointment(data.appointmentId)
            this.position = position
        }
        binding.recyclerView.adapter = adapter
//        viewModel.getAppointment()
    }

    private fun observer() {
        viewModel.navigate.observe(viewLifecycleOwner){
            when(it){
                HomeNav.DRAWER->{
                    openDrawer()
                }
                HomeNav.STUDENT->{
                    findNavController().navigate(HomeScreenDirections.actionHomeScreenToUsers("student"))
                    closeDrawer()
                }
                HomeNav.CONSULTANT->{
                    findNavController().navigate(HomeScreenDirections.actionHomeScreenToUsers("consultant"))
                    closeDrawer()
                }
                HomeNav.LOGOUT->{
                    findNavController().navigate(HomeScreenDirections.actionHomeScreenToChooseType())
                }
                HomeNav.QUIZ->{
                    findNavController().navigate(R.id.action_homeScreen_to_selectSubject)
                    closeDrawer()
                }
                HomeNav.PAPER->{
                    findNavController().navigate(R.id.action_homeScreen_to_paper)
                    closeDrawer()
                }
                HomeNav.SCOPE->{
                    findNavController().navigate(R.id.action_homeScreen_to_scope)
                    closeDrawer()
                }
                else ->{

                }
            }
        }
        viewModel.response.observe(viewLifecycleOwner){
            if (it.status){
                if (!it.appointmentArray.isNullOrEmpty()) {
                    viewModel.isListEmpty.set(false)
                    adapter.updateList(it.appointmentArray)
                    binding.TVAppointment.text = "no of appointments: ${it.appointmentArray.size}"
                    Log.d("checking",it.appointmentArray.toString())
                } else {
                    binding.TVAppointment.text = "no of appointments: 0"
//                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
        viewModel.delete.observe(viewLifecycleOwner){
            Util.hide_loader()
            if (it.status){
                findNavController().navigate(R.id.action_homeScreen_self)
            }
        }
    }

    private fun closeDrawer() {
        binding.drawerLayout.closeDrawer(Gravity.LEFT)
    }

    private fun openDrawer() {
        binding.drawerLayout.openDrawer(Gravity.LEFT)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.navigate.value = null
    }
}