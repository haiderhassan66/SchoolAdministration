package com.example.schooladministration.repo

import kotlinx.coroutines.flow.Flow

interface IDataStoreRepo {
    suspend fun saveUser(user: String)
    suspend fun getUser():Flow<String>
    suspend fun saveType(type: String)
    suspend fun getType():Flow<String>
}