package com.example.schooladministration.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel:ViewModel() {

    var navigate = MutableLiveData<HomeNav>()

    fun drawerClick(){
        navigate.value = HomeNav.DRAWER
    }
    fun clickPaper(){

    }
    fun clickStudent(){
        navigate.value = HomeNav.STUDENT
    }
    fun clickCounsultant(){

    }
    fun clickQuiz(){

    }
    fun clickProfile(){

    }
    fun clickLogout(){

    }
}
enum class HomeNav{
    DRAWER,STUDENT,CONSULTANT,QUIZ,PROFILE,LOGOUT
}