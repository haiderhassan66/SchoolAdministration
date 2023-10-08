package com.example.schooladministration.model

data class QuizModel(
    val id:String,
    val question:String,
    val option1:String,
    val option2:String,
    val option3:String,
    val option4:String,
    val result:String,
    val subject:String
){
    constructor():this("","","","","","","","")
}