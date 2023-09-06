package com.example.schooladministration.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.example.schooladministration.model.SignUpModel

class UsersAdapterViewModel(val data:SignUpModel):ViewModel() {
    var nameObservable = ObservableField(data.name)
    var emailObservable = ObservableField(data.email)
    var classObservable = ObservableField(data.education)
}