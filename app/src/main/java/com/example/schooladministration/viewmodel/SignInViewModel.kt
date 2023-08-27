package com.example.schooladministration.viewmodel

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SignInViewModel:ViewModel() {
    var validEmail = ObservableField(true)
    var validPassword = ObservableField(true)
    var emailText = MutableLiveData("")
    var passwordText = MutableLiveData("")

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
}

enum class SignInNav{
    SIGNIN,SIGNUP
}