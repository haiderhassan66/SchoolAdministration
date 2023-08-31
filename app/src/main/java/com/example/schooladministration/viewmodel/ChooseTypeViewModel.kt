package com.example.schooladministration.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.schooladministration.model.SignUpModel
import com.example.schooladministration.repo.DataStoreRepo
import com.example.schooladministration.utils.Singleton
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class ChooseTypeViewModel :ViewModel() {
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