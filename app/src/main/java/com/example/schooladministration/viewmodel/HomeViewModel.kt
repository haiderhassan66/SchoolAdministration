package com.example.schooladministration.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.schooladministration.db.Firebase
import com.example.schooladministration.model.Response
import com.example.schooladministration.repo.DataStoreRepo
import com.example.schooladministration.utils.Singleton
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val dataStore:DataStoreRepo, private val firebase: Firebase):ViewModel() {

    var name = ObservableField<String>()
    var navigate = MutableLiveData<HomeNav>()
    var response = MutableLiveData<Response>()
    var delete = MutableLiveData<Response>()
    var isListEmpty = ObservableField(true)

    fun drawerClick(){
        navigate.value = HomeNav.DRAWER
    }
    fun clickPaper(){
        navigate.value = HomeNav.PAPER
    }

    fun clickScope(){
        navigate.value = HomeNav.SCOPE
    }
    fun clickStudent(){
        navigate.value = HomeNav.STUDENT
    }
    fun clickConsultant(){
        navigate.value = HomeNav.CONSULTANT
    }
    fun clickQuiz(){
        navigate.value = HomeNav.QUIZ
    }
    fun clickProfile(){

    }
    fun clickLogout(){
        logout()
        navigate.value = HomeNav.LOGOUT
    }

    private fun logout(){
        viewModelScope.launch(Dispatchers.IO) {
            dataStore.saveUser("")
            Singleton.user = null
        }
        viewModelScope.launch(Dispatchers.IO) {
            dataStore.saveType("")
        }
    }

    fun getAppointment(){
        viewModelScope.launch {
            firebase.getAppointment {
                response.value = it
            }
        }
    }
    fun studentAppointment(id:String){
        viewModelScope.launch {
            firebase.getUserAppointment(id){
                response.value = it
            }
        }
    }

    fun consultantAppointment(id:String){
        viewModelScope.launch {
            firebase.getConsultantAppointment(id){
                response.value = it
            }
        }
    }

    fun deleteAppointment(id:String){
        viewModelScope.launch {
            firebase.deleteAppointment(id){
                delete.value = it
            }
        }
    }
}
enum class HomeNav{
    DRAWER,STUDENT,CONSULTANT,QUIZ,PAPER,LOGOUT,SCOPE
}