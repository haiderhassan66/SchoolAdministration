package com.example.schooladministration.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.schooladministration.db.Firebase
import com.example.schooladministration.model.QuizModel
import com.example.schooladministration.model.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestionsViewModel @Inject constructor(private val fb:Firebase):ViewModel() {
    var questions = MutableLiveData<Response>()
    var deleteQuestion = MutableLiveData<Response>()

    fun getQuestions(subject:String){
        viewModelScope.launch(Dispatchers.IO){
            fb.getQuizQuestions(subject){
                questions.value = it
            }
        }
    }

    fun deleteQuestion(id:String){
        viewModelScope.launch(Dispatchers.IO){
            fb.deleteQuiz(id){
                deleteQuestion.value = it
            }
        }
    }
}