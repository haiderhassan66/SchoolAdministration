package com.example.schooladministration.db

import android.net.Uri
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

    suspend fun addQuizQuestion(
        question:String,
        option1:String,
        option2:String,
        option3:String,
        option4:String,
        result:String,
        subject:String,
        onComplete: (Response?) -> Unit
    )

    suspend fun getQuizQuestions(
        subject: String,
        onComplete: (Response?) -> Unit
    )

    suspend fun deleteQuiz(
        id:String,
        onComplete: (Response?) -> Unit
    )

    suspend fun uploadImage(
        uri:Uri,
        subject:String,
        onComplete: (Response?) -> Unit
    )

    suspend fun getPapers(
        subject: String,
        onComplete: (Response?) -> Unit
    )

    suspend fun deletePaper(
        id:String,
        onComplete: (Response?) -> Unit
    )

//    suspend fun uploadPaper(
//        imageUrl:String,
//        onComplete: (Response?) -> Unit
//    )
}