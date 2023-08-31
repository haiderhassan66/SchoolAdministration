package com.example.schooladministration.model

data class Response(
    val status:Boolean,
    val message:String?,
    val data:SignUpModel?
)
