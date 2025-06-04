package com.cloudsurfe.jellienotes.modules.data

import com.cloudsurfe.jellienotes.modules.domain.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import jakarta.inject.Inject
import kotlinx.coroutines.tasks.await

class AuthRepositoryImpl @Inject constructor(private val firebaseAuth: FirebaseAuth) :
    AuthRepository {
    override suspend fun registerUserWithEmailAndPassword(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            val authResult = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            authResult.user?.let {
                onSuccess()
            } ?: onError("Something went wrong while registering user")

        } catch (e: Exception) {
            onError(e.message ?: "Something went wrong while registering user")
        }

    }

    override suspend fun signInUserWithEmailAndPassword(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            val authResult = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            authResult.user?.let {
                onSuccess()
            } ?: onError("Something went wrong while signing in user")

        } catch (e: Exception) {
            onError(e.message ?: "Something went wrong while signing in user")
        }
    }

//    override suspend fun signInWithGoogle(
//        onSuccess: () -> Unit,
//        onError: (String) -> Unit
//    ) {
//        TODO("Not yet implemented")
//    }
//
//    override suspend fun signInWithMicrosoft(
//        onSuccess: () -> Unit,
//        onError: (String) -> Unit
//    ) {
//        TODO("Not yet implemented")
//    }

    override fun isUserAuthenticated(): Boolean {
        return firebaseAuth.currentUser != null
    }

    override suspend fun sendPasswordResetEmail(
        email: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            firebaseAuth.sendPasswordResetEmail(email).addOnSuccessListener {
                onSuccess()
            }.addOnFailureListener {
                onError(it.message ?: "Something went wrong while sending password reset email")
            }


        }catch (e:Exception){
            onError(e.message ?: "Something went wrong while sending password reset email")
        }

    }

    override suspend fun signOut(): Result<Unit> {
        return try {
            firebaseAuth.signOut()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}