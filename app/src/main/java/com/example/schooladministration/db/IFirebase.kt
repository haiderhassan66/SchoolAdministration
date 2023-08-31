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
        onComplete: (Boolean, String) -> Unit
    )
}