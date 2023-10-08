package com.example.schooladministration.db

import android.net.Uri
import android.util.Log
import com.example.schooladministration.model.AppointmentModel
import com.example.schooladministration.model.PaperImageModel
import com.example.schooladministration.model.QuizModel
import com.example.schooladministration.model.Response
import com.example.schooladministration.model.SignUpModel
import com.google.api.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Calendar

class Firebase : IFirebase {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance()
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

    override suspend fun addQuizQuestion(
        question: String,
        option1: String,
        option2: String,
        option3: String,
        option4: String,
        result: String,
        subject: String,
        onComplete: (Response?) -> Unit
    ) {
        val collection = db.collection("quiz")
        val id = collection.document().id
        val quizQuestion =
            QuizModel(id,question, option1, option2, option3, option4, result, subject)

        collection.document(id)
            .set(quizQuestion)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onComplete(Response(true, "Question added!"))
                }
            }.addOnFailureListener { e ->
                onComplete(Response(false, e.message.toString()))
            }
    }

    override suspend fun getQuizQuestions(subject: String, onComplete: (Response?) -> Unit) {
        db.collection("quiz")
            .whereEqualTo("subject", subject)
            .get()
            .addOnSuccessListener { querySnapshot ->
                val quizQuestion = mutableListOf<QuizModel>()
                for (document in querySnapshot) {
                    val quiz = document.toObject(QuizModel::class.java)
                    quizQuestion.add(quiz)
                }
                if (quizQuestion.isNotEmpty()) {
                    onComplete(Response(true, "success", quizArray = quizQuestion))
                }
                else {
                    onComplete(Response(true, "quiz not found", quizArray = quizQuestion))
                }

            }.addOnFailureListener {
                onComplete(Response(false, it.message.toString(), null))
            }
    }

    override suspend fun deleteQuiz(id: String, onComplete: (Response?) -> Unit) {
        db.collection("quiz")
            .document(id)
            .delete()
            .addOnSuccessListener {
                onComplete(Response(true,"Deleted"))
            }.addOnFailureListener {
                onComplete(Response(false, it.message.toString()))
            }
    }

    override suspend fun uploadImage(uri: Uri, subject: String,onComplete: (Response?) -> Unit) {
        val timeStamp = Calendar.getInstance().timeInMillis
        val imageRef = storage.reference.child("paperImages/$timeStamp.jpg")
        imageRef.putFile(uri)
            .addOnSuccessListener {
                imageRef.downloadUrl.addOnSuccessListener { downloadUri ->
                    val imageUrl = downloadUri.toString()
                    uploadPaper(imageUrl,subject){
                        onComplete(it)
                    }
                }
            }
            .addOnFailureListener {
                onComplete(Response(false, it.message.toString()))
            }
    }

    private fun uploadPaper(imageUrl: String, subject:String, onComplete: (Response?) -> Unit) {
        val collection = db.collection("papers")
        val id = collection.document().id
        val paperImage =
            PaperImageModel(id, imageUrl,subject)

        collection.document(id)
            .set(paperImage)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onComplete(Response(true, "Paper Uploaded!"))
                }
            }.addOnFailureListener { e ->
                onComplete(Response(false, e.message.toString()))
            }
    }

    override suspend fun getPapers(subject: String, onComplete: (Response?) -> Unit) {
        db.collection("papers")
            .whereEqualTo("subject", subject)
            .get()
            .addOnSuccessListener { querySnapshot ->
                val papers = mutableListOf<PaperImageModel>()
                for (document in querySnapshot) {
                    val paperDetail = document.toObject(PaperImageModel::class.java)
                    papers.add(paperDetail)
                }
                if (papers.isNotEmpty()) {
                    onComplete(Response(true, "success", papersArray = papers))
                }
                else {
                    onComplete(Response(true, "papers not found", papersArray = papers))
                }

            }.addOnFailureListener {
                onComplete(Response(false, it.message.toString(), null))
            }
    }

    override suspend fun deletePaper(id: String, onComplete: (Response?) -> Unit) {
        db.collection("papers")
            .document(id)
            .delete()
            .addOnSuccessListener {
                onComplete(Response(true,"Deleted"))
            }.addOnFailureListener {
                onComplete(Response(false, it.message.toString()))
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