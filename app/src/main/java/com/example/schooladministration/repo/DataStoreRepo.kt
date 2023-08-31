package com.example.schooladministration.repo

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


const val DataStore_NAME = "ONLINETUTOR"


val Context.datastore  by  preferencesDataStore(name = DataStore_NAME)

class DataStoreRepo(private val context: Context):IDataStoreRepo {

    companion object{
        val USER = stringPreferencesKey("USER")
        val TYPE = stringPreferencesKey("TYPE")
    }

    override suspend fun saveUser(user: String) {
        context.datastore.edit {
            it[USER]=user
        }
    }

    override suspend fun getUser()= context.datastore.data.map {
        it[USER]?:""
    }

    override suspend fun saveType(type: String) {
        context.datastore.edit {
            it[TYPE]=type
        }
    }

    override suspend fun getType()= context.datastore.data.map {
        it[TYPE]?:""
    }
}