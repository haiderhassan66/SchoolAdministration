package com.example.schooladministration.di

import com.example.schooladministration.db.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object FirebaseModule {

    @Provides
    @ViewModelScoped
    fun provideFirebase(): Firebase {
        return Firebase.getInstance()
    }
}