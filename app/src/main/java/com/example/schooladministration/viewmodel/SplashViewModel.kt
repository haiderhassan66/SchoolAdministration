package com.example.schooladministration.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.schooladministration.model.SignUpModel
import com.example.schooladministration.repo.DataStoreRepo
import com.example.schooladministration.utils.Singleton.type
import com.example.schooladministration.utils.Singleton.user
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val dataStoreRepo: DataStoreRepo):ViewModel() {
    init {
        getData()
    }
    fun getData(){
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepo.getUser().collect(){
                if (!it.equals("")){
                    user = Gson().fromJson(it, SignUpModel::class.java)
                    Log.d("checking", user.toString())
                }
            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepo.getType().collect(){
                if (!it.equals("")){
                    Log.d("checking",it)
                    type = it
                }
            }
        }
    }
}