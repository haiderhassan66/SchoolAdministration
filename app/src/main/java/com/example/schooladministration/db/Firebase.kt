package com.example.schooladministration.db

import android.util.Log
import com.example.schooladministration.model.AppointmentModel
import com.example.schooladministration.model.Response
import com.example.schooladministration.model.SignUpModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.firestore.FirebaseFirestore

class Firebase : IFirebase {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()
    override suspend fun signIn(
        email: String,
        password: String,
        type: String,
        onComplete: (Response?) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val userId = auth.uid!!
                    db.collection(type).document(userId)
                        .get()
                        .addOnSuccessListener { document ->
                            if (document.exists()) {
                                val userData = document.toObject(SignUpModel::class.java)
                                if (userData != null) {
                                    onComplete(Response(true, "Signed In !", userData))
                                }
                            } else {
                                onComplete(
                                    Response(
                                        false,
                                        "Error while logging in try again later",
                                        null
                                    )
                                )
                            }
                        }.addOnFailureListener {

                            onComplete(Response(false, it.message.toString(), null))
                        }
                } else {
                    onComplete(Response(false, it.exception?.message.toString(), null))
                }
            }.addOnFailureListener {
                onComplete(Response(false, it.message.toString(), null))
            }
    }

    override suspend fun signUp(
        email: String,
        password: String,
        education: String,
        name: String,
        type: String,
        onComplete: (Response?) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val userId = auth.uid!!
                    val user = SignUpModel(userId, name, education, email)
                    db.collection(type).document(userId)
                        .set(user)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                onComplete(Response(true, "Account Created!", user))
                            }
                        }.addOnFailureListener { e ->
                            onComplete(Response(false, e.message.toString()))
                        }
                } else {
                    // Sign-up failed
                    if (it.exception is FirebaseAuthUserCollisionException) {
                        onComplete(Response(false, "User with this email already exists"))
                    } else {
                        onComplete(Response(false, "Sign-up failed: ${it.exception?.message}"))
                    }
                }
            }.addOnFailureListener {
                onComplete(Response(false, it.message.toString()))
            }
    }

    override suspend fun getUsers(type: String, onComplete: (Response?) -> Unit) {
        db.collection(type)
            .get()
            .addOnSuccessListener { querySnapshot ->
                val userList = mutableListOf<SignUpModel>()
                for (document in querySnapshot) {
                    val userData = document.toObject(SignUpModel::class.java)
                    userList.add(userData)
                }
                if (userList.isNotEmpty())
                    onComplete(Response(true, "success", dataArray = userList))
                else
                    onComplete(Response(true, "user not found", dataArray = userList))

            }.addOnFailureListener {

                onComplete(Response(false, it.message.toString(), null))
            }
    }

    override suspend fun getUserWithEducation(
        type: String,
        education: String,
        onComplete: (Response?) -> Unit
    ) {
        db.collection(type)
            .whereEqualTo("education",education)
            .get()
            .addOnSuccessListener { querySnapshot ->
                val userList = mutableListOf<SignUpModel>()
                for (document in querySnapshot) {
                    val userData = document.toObject(SignUpModel::class.java)
                    userList.add(userData)
                }
                if (userList.isNotEmpty())
                    onComplete(Response(true, "success", dataArray = userList))
                else
                    onComplete(Response(true, "user not found", dataArray = userList))

            }.addOnFailureListener {

                onComplete(Response(false, it.message.toString(), null))
            }
    }

    override suspend fun saveAppointment(
        studentName: String,
        studentId: String,
        consultantName: String,
        consultantId: String,
        timeStamp: Long,
        onComplete: (Response?) -> Unit
    ) {
        val collection = db.collection("appointment")
        val id = collection.document().id
        val appointment =
            AppointmentModel(id, studentName, studentId, consultantName, consultantId, timeStamp)

        collection.document(id)
            .set(appointment)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onComplete(Response(true, "Appointment Created!"))
                }
            }.addOnFailureListener { e ->
                onComplete(Response(false, e.message.toString()))
            }
    }

    override suspend fun getAppointment(onComplete: (Response?) -> Unit) {
        db.collection("appointment")
            .get()
            .addOnSuccessListener { querySnapshot ->
                val appointments = mutableListOf<AppointmentModel>()
                for (document in querySnapshot) {
                    val appointmentDetail = document.toObject(AppointmentModel::class.java)
                    appointments.add(appointmentDetail)
                }
                if (appointments.isNotEmpty()) {
                    onComplete(Response(true, "success", appointmentArray = appointments))
                }
                else {
                    onComplete(Response(true, "user not found", appointmentArray = appointments))
                }

            }.addOnFailureListener {
                onComplete(Response(false, it.message.toString(), null))
            }
    }

    override suspend fun getUserAppointment(studentId: String, onComplete: (Response?) -> Unit) {
        db.collection("appointment")
            .whereEqualTo("studentId",studentId)
            .get()
            .addOnSuccessListener { querySnapshot ->
                val appointments = mutableListOf<AppointmentModel>()
                for (document in querySnapshot) {
                    val appointmentDetail = document.toObject(AppointmentModel::class.java)
                    appointments.add(appointmentDetail)
                }
                if (appointments.isNotEmpty()) {
                    onComplete(Response(true, "success", appointmentArray = appointments))
                }
                else {
                    onComplete(Response(true, "user not found", appointmentArray = appointments))
                }

            }.addOnFailureListener {
                onComplete(Response(false, it.message.toString(), null))
            }
    }

    override suspend fun getConsultantAppointment(
        consultantId: String,
        onComplete: (Response?) -> Unit
    ) {
        db.collection("appointment")
            .whereEqualTo("consultantId",consultantId)
            .get()
            .addOnSuccessListener { querySnapshot ->
                val appointments = mutableListOf<AppointmentModel>()
                for (document in querySnapshot) {
                    val appointmentDetail = document.toObject(AppointmentModel::class.java)
                    appointments.add(appointmentDetail)
                }
                if (appointments.isNotEmpty()) {
                    onComplete(Response(true, "success", appointmentArray = appointments))
                }
                else {
                    onComplete(Response(true, "user not found", appointmentArray = appointments))
                }

            }.addOnFailureListener {
                onComplete(Response(false, it.message.toString(), null))
            }
    }

    override suspend fun deleteAppointment(appointmentId: String, onComplete: (Response?) -> Unit) {
        db.collection("appointment")
            .document(appointmentId)
            .delete()
            .addOnSuccessListener {
                onComplete(Response(true,"Deleted", null))
            }.addOnFailureListener {
                onComplete(Response(false, it.message.toString(), null))
            }
    }

    companion object {
        @Volatile
        private var instance: Firebase? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: Firebase().also { instance = it }
            }
    }
}