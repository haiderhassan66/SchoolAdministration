package com.example.schooladministration.view.paper.paperdetails

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.schooladministration.databinding.FragmentPaperDetailsBinding
import com.example.schooladministration.utils.Singleton
import com.example.schooladministration.utils.Util
import com.example.schooladministration.viewmodel.PaperDetailsViewModel
import com.github.dhaval2404.imagepicker.ImagePicker
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaperDetails : Fragment() {
    private var _binding:FragmentPaperDetailsBinding?=null
    private val binding get() = _binding!!
    private val viewModel:PaperDetailsViewModel by viewModels()
    private val args:PaperDetailsArgs by navArgs()
    private lateinit var adapter: PaperImageAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPaperDetailsBinding.inflate(inflater, container, false)

        binding.viewModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        if (args.id == 0){
//            binding.viewModel!!.picObservable.set(true)
//            binding.viewModel!!.textObservable.set(false)
//        } else {
//            binding.viewModel!!.picObservable.set(false)
//            binding.viewModel!!.textObservable.set(true)
//        }
        initial()
        apiCall()
        setAdapter()
        observer()
        clickListener()
    }

    private fun initial() {
        if (Singleton.type=="student"){
            binding.add.visibility = View.GONE
        }
    }

    private fun apiCall() {
        viewModel.getPapers(args.subject)
    }

    private fun observer() {
        viewModel.uploadResponse.observe(viewLifecycleOwner){
            Util.hide_loader()
            if (it.status){
                viewModel.getPapers(args.subject)
            }
        }
        viewModel.papersResponse.observe(viewLifecycleOwner){
            if (it.status){
                if (it.papersArray!!.isNotEmpty()){
                    adapter.updateList(it.papersArray)
                    viewModel.textObservable.set(false)
                } else {
                    adapter.updateList(it.papersArray)
                    viewModel.textObservable.set(true)
                }

            }
        }
        viewModel.deleteResponse.observe(viewLifecycleOwner){
            Util.hide_loader()
            if (it.status){
                viewModel.getPapers(args.subject)
            }
        }
    }

    private fun clickListener() {
        binding.add.setOnClickListener {
            pickImage()
        }
    }

    private fun setAdapter() {
        adapter = PaperImageAdapter{
            Util.show_loader(requireContext())
            viewModel.deletePaper(it.paperId)
        }

        binding.paperRv.adapter = adapter
    }

    private fun pickImage(){
        ImagePicker.with(this)
            .crop()
            .compress(128)
            .maxResultSize(1080, 1080)
            .galleryOnly()	//User can only select image from Gallery
            .createIntent { intent ->
                startForProfileImageResult.launch(intent)
            }
    }

    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            when (resultCode) {
                Activity.RESULT_OK -> {
                    //Image Uri will not be null for RESULT_OK
                    val fileUri = data?.data!!
                    Util.show_loader(requireContext())
                    viewModel.uploadImage(args.subject,fileUri)


                }
                ImagePicker.RESULT_ERROR -> {
                    Toast.makeText(requireContext(), ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Toast.makeText(requireContext(), "Task Cancelled", Toast.LENGTH_SHORT).show()
                }
            }
        }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}