package com.example.schooladministration.viewmodel

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
class QuizViewModel @Inject constructor(private val fb:Firebase):ViewModel() {

    var response = MutableLiveData<Response>()
    fun getQuestions(subject:String){
        viewModelScope.launch(Dispatchers.IO){
            fb.getQuizQuestions(subject){
                response.value = it
            }
        }
    }
}