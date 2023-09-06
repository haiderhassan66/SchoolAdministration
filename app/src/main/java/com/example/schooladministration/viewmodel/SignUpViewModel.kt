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
import com.example.schooladministration.db.Firebase
import com.example.schooladministration.db.IFirebase
import com.example.schooladministration.model.Response
import com.example.schooladministration.repo.DataStoreRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(private val firebase:Firebase, private val datastore: DataStoreRepo):ViewModel() {
    var validEmail = ObservableField<Boolean>()
    var validPassword = ObservableField<Boolean>()
    var validRetypePassword = ObservableField<Boolean>()
    var validName = ObservableField<Boolean>()
//    var spinnerItems = MutableLiveData<Array<String>>()
    val spinnerItems = MutableLiveData<List<String>>()
    var emailText = MutableLiveData("")
    var nameText = MutableLiveData("")
    var passwordText = MutableLiveData("")
    var retypePasswordText = MutableLiveData("")
    var type = MutableLiveData("")
    var education:String = "Matriculation"
    var signUpNav = MutableLiveData<SignInNav>()
    var reponse = MutableLiveData<Response>()

    val spinnerItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            // Handle the item selection event here
            education = spinnerItems.value?.get(position).toString()
//            Log.d("checking",spinnerItems.value?.get(position).toString())
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {
            // Handle case when nothing is selected
        }
    }

    fun createBtnClick(){
        if (validPassword.get()!! && validRetypePassword.get()!! && validEmail.get()!! && validName.get()!!){
            signUpNav.value = SignInNav.SIGNUP
        }
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
                checkPassword()
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        }
    }

    private fun checkPassword():Boolean {
        return if (passwordText.value.toString().length>=8){
            validPassword.set(true)
            true
        } else {
            validPassword.set(false)
            false
        }
    }

    private fun checkReTypePassword():Boolean {
        return if (retypePasswordText.value.toString() == passwordText.value.toString()){
            validRetypePassword.set(true)
            true
        } else {
            validRetypePassword.set(false)
            false
        }
    }

    fun retypePasswordTextWatcher():TextWatcher{
        return object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                checkReTypePassword()
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

    fun signUp(){
        viewModelScope.launch(Dispatchers.IO) {
            firebase.signUp(emailText.value.toString(),passwordText.value.toString(),education,nameText.value.toString(),type.value.toString()){
                reponse.value = it
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
    fun saveData(user:String,type: String){
        viewModelScope.launch(Dispatchers.IO) {
             datastore.saveUser(user)
        }

        viewModelScope.launch(Dispatchers.IO) {
            datastore.saveType(type)
        }
    }
}