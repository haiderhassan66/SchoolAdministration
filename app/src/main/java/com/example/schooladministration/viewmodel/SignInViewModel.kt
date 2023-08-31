package com.example.schooladministration.viewmodel

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.schooladministration.model.Response
import com.example.schooladministration.repo.DataStoreRepo
import com.example.schooladministration.db.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(private val firebase: Firebase, private val datastore:DataStoreRepo):ViewModel() {
    var validEmail = ObservableField(true)
    var validPassword = ObservableField(true)
    var emailText = MutableLiveData("")
    var passwordText = MutableLiveData("")

    var signinResponse = MutableLiveData<Response>()

    var navigate = MutableLiveData<SignInNav>()

    fun signinClick(){
        navigate.value = SignInNav.SIGNIN
    }

    fun signupClick(){
        navigate.value = SignInNav.SIGNUP
    }

    fun emailTextWatcher():TextWatcher{
        return object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {

            }

        }
    }

    fun passwordTextWatcher():TextWatcher{
        return object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {

            }

        }
    }

    fun signin(type:String){
        viewModelScope.launch(Dispatchers.IO) {
            firebase.signIn(emailText.value.toString(),passwordText.value.toString(),type){
                signinResponse.value = it
            }
        }
    }

    fun saveData(user:String,type: String){
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("checking user","user")
            datastore.saveUser(user)
        }

        viewModelScope.launch(Dispatchers.IO) {
            Log.d("checking user","type")
            datastore.saveType(type)
        }
    }
}

enum class SignInNav{
    SIGNIN,SIGNUP
}