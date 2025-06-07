package com.cloudsurfe.jellienotes.modules.domain.repository.auth_repo

import android.content.Context

public interface AuthRepository {

    public suspend fun registerUserWithEmailAndPassword(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    )

    public suspend fun signInUserWithEmailAndPassword(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    )

    public suspend fun signInWithGoogle(
        context: Context,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    )

    suspend fun signInWithMicrosoft(onSuccess: () -> Unit, onError: (String) -> Unit)

    public fun isUserAuthenticated(): Boolean

    public suspend fun sendPasswordResetEmail(
        email: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    )

    public suspend fun signOut(): Result<Unit>
}