package com.example.schooladministration.model

data class Response(
    val status:Boolean,
    val message:String?,
    val data:SignUpModel?=null,
    val dataArray: List<SignUpModel>?=null,
    val appointment:AppointmentModel?=null,
    val appointmentArray:List<AppointmentModel>?=null
)
