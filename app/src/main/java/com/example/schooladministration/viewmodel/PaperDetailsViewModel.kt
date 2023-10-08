package com.example.schooladministration.viewmodel

import android.net.Uri
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.schooladministration.db.Firebase
import com.example.schooladministration.model.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaperDetailsViewModel @Inject constructor(private val firebase: Firebase):ViewModel() {
    var textObservable = ObservableField(true)
    var picObservable = ObservableField(false)
    var uploadResponse = MutableLiveData<Response>()
    var papersResponse = MutableLiveData<Response>()
    var deleteResponse = MutableLiveData<Response>()

    fun uploadImage(subject:String,uri:Uri){
        viewModelScope.launch(Dispatchers.IO) {
            firebase.uploadImage(uri,subject){
                uploadResponse.value = it
            }
        }
    }

    fun getPapers(subject: String){
        viewModelScope.launch(Dispatchers.IO) {
            firebase.getPapers(subject){
                papersResponse.value = it
            }
        }
    }

    fun deletePaper(id:String){
        viewModelScope.launch(Dispatchers.IO){
            firebase.deletePaper(id){
                deleteResponse.value = it
            }
        }
    }
}