package com.cloudsurfe.jellienotes.di

import android.content.Context
import com.cloudsurfe.jellienotes.data.setting.SettingsDataStore
import com.cloudsurfe.jellienotes.data.setting.SettingsDataStoreImpl
import com.cloudsurfe.jellienotes.modules.data.repositoryImpl.auth_repo_impl.AuthRepositoryImpl
import com.cloudsurfe.jellienotes.modules.domain.repository.auth_repo.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providesSettingsDataStore(@ApplicationContext context: Context): SettingsDataStore {
        return SettingsDataStoreImpl(context)
    }

    @Provides
    @Singleton
    fun providesFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun providesAuthRepository(
        firebaseAuth: FirebaseAuth,
        @ApplicationContext context: Context
    ): AuthRepository {
        return AuthRepositoryImpl(firebaseAuth,context)
    }

}