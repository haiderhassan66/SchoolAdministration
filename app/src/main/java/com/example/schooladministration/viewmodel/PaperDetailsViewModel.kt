package com.example.schooladministration.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel

class PaperDetailsViewModel:ViewModel() {
    var textObservable = ObservableField(true)
    var picObservable = ObservableField(false)
}