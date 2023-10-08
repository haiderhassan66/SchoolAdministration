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
class AddQuestionViewModel @Inject constructor(private val fb:Firebase):ViewModel() {

    var questionText = MutableLiveData("")
    var option1Text = MutableLiveData("")
    var option2Text = MutableLiveData("")
    var option3Text = MutableLiveData("")
    var option4Text = MutableLiveData("")
    var answerText = MutableLiveData("")
    var response = MutableLiveData<Response>()
    var navigate = MutableLiveData<Navigate>()

    fun addClick(){
        if (!questionText.value.isNullOrBlank() &&
        !option1Text.value.isNullOrBlank() &&
        !option2Text.value.isNullOrBlank() &&
        !option3Text.value.isNullOrBlank() &&
        !option4Text.value.isNullOrBlank() &&
        !answerText.value.isNullOrBlank()){
            navigate.value = Navigate.DONE
        } else {
            navigate.value = Navigate.ERROR
        }
    }

    fun addQuestion(subject:String){
        viewModelScope.launch(Dispatchers.IO){
            fb.addQuizQuestion(questionText.value.toString(),
                option1Text.value.toString(),
                option2Text.value.toString(),
                option3Text.value.toString(),
                option4Text.value.toString(),
                answerText.value.toString(),
                subject){
                response.value = it
            }
        }
    }

    enum class Navigate{
        ERROR,DONE
    }
}