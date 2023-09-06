package com.example.schooladministration.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.schooladministration.db.Firebase
import com.example.schooladministration.model.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppointmentViewModel @Inject constructor(private val firebase:Firebase):ViewModel() {

    var nameObservable = ObservableField("")
    var dateObservable = ObservableField("")
    var timeObservable = ObservableField("")

    var navigate = MutableLiveData<AppointmentNav>()
    var response = MutableLiveData<Response>()


    fun confirmClick(){
        if (!nameObservable.get().equals("")&&!dateObservable.get().equals("")&&!timeObservable.get().equals("")){
            navigate.value = AppointmentNav.CONFIRM
        }
    }

    fun dateClick(){
        navigate.value = AppointmentNav.DATE
    }

    fun timeClick(){
        navigate.value = AppointmentNav.TIME
    }

    fun saveAppointment(
        studentName:String,
        studentId:String,
        consultantName:String,
        consultantId:String,
        timeStamp:Long,
    ){
        viewModelScope.launch {
            firebase.saveAppointment(studentName,studentId,consultantName,consultantId,timeStamp){
                response.value = it
            }
        }
    }
}
enum class AppointmentNav{
    CONFIRM,DATE,TIME
}