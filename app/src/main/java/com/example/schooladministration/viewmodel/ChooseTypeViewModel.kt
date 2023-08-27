package com.example.schooladministration.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ChooseTypeViewModel:ViewModel() {
    var Navigate = MutableLiveData<ChooseEnum>()

    fun consultant(){
        Navigate.value = ChooseEnum.CONSULTANT
    }
    fun student(){
        Navigate.value = ChooseEnum.STUDENT
    }
    fun admin(){
        Navigate.value = ChooseEnum.ADMIN
    }
}
enum class ChooseEnum{
    CONSULTANT,STUDENT,ADMIN
}