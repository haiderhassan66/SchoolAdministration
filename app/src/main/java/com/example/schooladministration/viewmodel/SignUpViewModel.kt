package com.example.schooladministration.viewmodel

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.AdapterView
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.schooladministration.R
import com.example.schooladministration.utils.Firebase
import kotlinx.coroutines.launch

class SignUpViewModel:ViewModel() {
    var validEmail = ObservableField(true)
    var validPassword = ObservableField(true)
    var validRetypePassword = ObservableField(true)
    var validName = ObservableField(true)
//    var spinnerItems = MutableLiveData<Array<String>>()
    val spinnerItems = MutableLiveData<List<String>>()
    var emailText = MutableLiveData("")
    var nameText = MutableLiveData("")
    var passwordText = MutableLiveData("")
    var retypePasswordText = MutableLiveData("")
    var type = MutableLiveData("")
    var education:String = "Matriculation"
    var signUpNav = MutableLiveData<SignInNav>()

    val spinnerItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            // Handle the item selection event here
            education = spinnerItems.value?.get(position).toString()
            Log.d("checking",spinnerItems.value?.get(position).toString())
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {
            // Handle case when nothing is selected
        }
    }

    fun createBtnClick(){
        signUpNav.value = SignInNav.SIGNUP
    }

    fun signupClick(){

    }



    fun emailTextWatcher():TextWatcher{
        return object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                checkemail()
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

    fun retypePasswordTextWatcher():TextWatcher{
        return object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {

            }

        }
    }

    fun nameTextWatcher():TextWatcher{
        return object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                checkName()
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        }
    }

    private fun checkName():Boolean {
        return if (!nameText.value.toString().isNullOrBlank()){
            validName.set(true)
            true
        } else {
            validEmail.set(false)
            false
        }
    }

    fun checkemail():Boolean{

        if (Patterns.EMAIL_ADDRESS.matcher(emailText.value.toString()).matches()){
            validEmail.set(true)
            return true
        }else{
            validEmail.set(false)
            return false
        }
    }
}