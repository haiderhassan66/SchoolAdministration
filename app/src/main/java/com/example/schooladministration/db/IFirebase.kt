package com.example.schooladministration.db

import com.example.schooladministration.model.Response

interface IFirebase {
    suspend fun signIn(
        email: String,
        password: String,
        type: String,
        onComplete: (Response?) -> Unit
    )
    suspend fun signUp(
        email: String,
        password: String,
        education: String,
        name: String,
        type: String,
        onComplete: (Response?) -> Unit
    )
    suspend fun getUsers(
        type:String,
        onComplete: (Response?) -> Unit
    )

    suspend fun getUserWithEducation(
        type:String,
        education:String,
        onComplete: (Response?) -> Unit
    )

    suspend fun getAppointment(
        onComplete: (Response?) -> Unit
    )

    suspend fun getUserAppointment(
        studentId:String,
        onComplete: (Response?) -> Unit
    )

    suspend fun getConsultantAppointment(
        consultantId:String,
        onComplete: (Response?) -> Unit
    )

    suspend fun deleteAppointment(
        appointmentId:String,
        onComplete: (Response?) -> Unit
    )

    suspend fun saveAppointment(
        studentName:String,
        studentId:String,
        consultantName:String,
        consultantId:String,
        timeStamp:Long,
        onComplete: (Response?) -> Unit
    )
}