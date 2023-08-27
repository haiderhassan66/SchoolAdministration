package com.example.schooladministration.model

data class SignUpModel(
    var userId:String,
    var name:String,
    var education:String,
    var email:String
){
    constructor():this("","","","")
}
