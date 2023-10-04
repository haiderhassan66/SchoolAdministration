package com.example.schooladministration.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AddQuestionViewModel:ViewModel() {

    var questionText = MutableLiveData("")
    var option1Text = MutableLiveData("")
    var option2Text = MutableLiveData("")
    var option3Text = MutableLiveData("")
    var option4Text = MutableLiveData("")
    var answerText = MutableLiveData("")

    fun addClick(){

    }
}