package com.example.schooladministration.utils

import android.R
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.databinding.BindingAdapter
import com.example.schooladministration.viewmodel.SignUpViewModel

@BindingAdapter("spinnerItems", "viewModel")
fun setSpinnerItemsAndViewModel(spinner: Spinner, items: List<String>?, viewModel: SignUpViewModel) {
    val context = spinner.context
    val adapter = ArrayAdapter(context, R.layout.simple_spinner_item, items ?: emptyList())
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    spinner.adapter = adapter

    // Set up a listener to handle item selection if needed
    spinner.onItemSelectedListener = viewModel.spinnerItemSelectedListener
}