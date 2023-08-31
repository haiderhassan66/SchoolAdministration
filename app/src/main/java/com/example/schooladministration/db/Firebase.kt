package com.example.schooladministration.db

import com.example.schooladministration.model.Response
import com.example.schooladministration.model.SignUpModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.firestore.FirebaseFirestore

class Firebase:IFirebase {

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
                                    onComplete(Response(true,null,userData))
                                }
                            } else {
                                onComplete(Response(false, "Error while logging in try again later", null))
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
        onComplete: (Boolean, String) -> Unit
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
                                onComplete(true, "")
                            }
                        }.addOnFailureListener { e ->
                            onComplete(false, e.message.toString())
                        }
                } else {
                    // Sign-up failed
                    if (it.exception is FirebaseAuthUserCollisionException) {
                        onComplete(false, "User with this email already exists")
                    } else {
                        onComplete(false, "Sign-up failed: ${it.exception?.message}")
                    }
                }
            }.addOnFailureListener {
                onComplete(false, it.message.toString())
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