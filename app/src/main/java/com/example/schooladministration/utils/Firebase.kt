package com.example.schooladministration.utils

import android.annotation.SuppressLint
import com.example.schooladministration.model.SignUpModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.firestore.FirebaseFirestore

class Firebase {

    companion object{
        private val auth = FirebaseAuth.getInstance()
        @SuppressLint("StaticFieldLeak")
        private val db = FirebaseFirestore.getInstance()

        fun signIn(email:String, password:String,type: String,onComplete:(Boolean,String,SignUpModel?)->Unit){
            auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener{
                    if (it.isSuccessful){
                        val userId = auth.uid!!
                        db.collection(type).document(userId)
                            .get()
                            .addOnSuccessListener {document->
                                if (document.exists()){
                                    val userData = document.toObject(SignUpModel::class.java)
                                    if (userData!=null){
                                        onComplete(true,"",userData)
                                    }
                                } else {
                                    onComplete(false,"Document not found",null)
                                }
                            }.addOnFailureListener {
                                onComplete(false,it.message.toString(),null)
                            }
                    } else {
                        onComplete(false,it.exception?.message.toString(),null)
                    }
                }.addOnFailureListener {
                    onComplete(false,it.message.toString(),null)
                }
        }

        fun signUp(email: String,password: String,education:String,name:String,type:String,onComplete: (Boolean,String) -> Unit){
            auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener {
                    if (it.isSuccessful){
                        val userId = auth.uid!!
                        val user = SignUpModel(userId,name,education,email)
                        db.collection(type).document(userId)
                            .set(user)
                            .addOnCompleteListener {task->
                                if (task.isSuccessful)
                                {
                                    onComplete(true,"")
                                }
                            }.addOnFailureListener { e ->
                                onComplete(false,e.message.toString())
                            }
                    } else {
                        // Sign-up failed
                        if (it.exception is FirebaseAuthUserCollisionException) {
                            onComplete(false,"User with this email already exists")
                        } else {
                            onComplete(false,"Sign-up failed: ${it.exception?.message}")
                        }
                    }
                }.addOnFailureListener {
                    onComplete(false,it.message.toString())
                }
        }
    }
}