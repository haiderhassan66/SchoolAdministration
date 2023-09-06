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
class UsersViewModel @Inject constructor(private val firebase: Firebase):ViewModel() {
    var isListEmpty = ObservableField(true)
    var navigate = MutableLiveData<UserAdapterNav>()
    var reponse = MutableLiveData<Response>()
    init {
        navigate.value = UserAdapterNav.ADAPTER
    }

    fun getUser(type:String){
        viewModelScope.launch {
            firebase.getUsers(type){
                reponse.value = it
            }
        }
    }

    fun getUserWithEducation(type: String,education:String){
        viewModelScope.launch {
            firebase.getUserWithEducation(type,education){
                reponse.value = it
            }
        }
    }

}
enum class UserAdapterNav{
    ADAPTER
}