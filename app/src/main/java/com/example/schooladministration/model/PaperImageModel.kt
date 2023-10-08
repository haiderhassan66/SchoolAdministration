package com.example.schooladministration.model

data class PaperImageModel(
    val paperId:String,
    val paperImg:String,
    val subject:String
){
    constructor():this("","","")
}