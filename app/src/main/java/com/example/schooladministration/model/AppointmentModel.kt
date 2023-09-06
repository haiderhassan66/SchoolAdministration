package com.example.schooladministration.model

data class AppointmentModel(
    val appointmentId:String,
    val studentName:String,
    val studentId:String,
    val consultantName:String,
    val consultantId:String,
    val timeStamp:Long
){
    constructor():this("","","","","",0)
}
