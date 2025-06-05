package com.cloudsurfe.jellienotes.modules.domain.repository.auth_repo

import android.content.Context

interface AuthRepository {

    suspend fun registerUserWithEmailAndPassword(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    )

    suspend fun signInUserWithEmailAndPassword(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    )

    suspend fun signInWithGoogle(
        context: Context,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    )

//    suspend fun signInWithMicrosoft(onSuccess: () -> Unit, onError: (String) -> Unit)

    fun isUserAuthenticated(): Boolean

    suspend fun sendPasswordResetEmail(
        email: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    )

    suspend fun signOut(): Result<Unit>
}