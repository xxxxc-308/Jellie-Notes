package com.cloudsurfe.jellienotes.modules.data.repositoryImpl.auth_repo_impl

import android.app.Activity
import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import com.cloudsurfe.jellienotes.core.LocalSecrets
import com.cloudsurfe.jellienotes.modules.domain.repository.auth_repo.AuthRepository
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.OAuthProvider
import kotlinx.coroutines.tasks.await

class AuthRepositoryImpl(
    private val firebaseAuth: FirebaseAuth,
    private val context: Context
) :
    AuthRepository {

    private val credentialManager = CredentialManager.create(context)

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

    override suspend fun signInWithGoogle(
        context: Context,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            val result = buildCredentialRequest()
            if (handleSingIn(result)) {
                onSuccess()
            } else {
                onError("Something went wrong while signing in with Google")
            }
        } catch (e: Exception) {
            onError(e.message ?: "Something went wrong while signing in with Google")

        }
    }

    override suspend fun signInWithMicrosoft(
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            val provider = OAuthProvider.newBuilder("microsoft.com")


            provider.addCustomParameter("prompt", "consent")

            provider.addCustomParameter("tenant", "common") // if doesn't work add actual tenant id from azure


            provider.scopes = listOf("email")


            val activity = context as? Activity ?: run {
                onError("Invalid context: must be an Activity.")
                return
            }

            firebaseAuth
                .startActivityForSignInWithProvider(activity, provider.build())
                .addOnSuccessListener {
                    onSuccess()
                }
                .addOnFailureListener { exception ->
                    onError(exception.localizedMessage ?: "Microsoft sign-in failed")
                }

        } catch (e: Exception) {
            onError(e.localizedMessage ?: "Unexpected error occurred")
        }
    }

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

        } catch (e: Exception) {
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

    private suspend fun handleSingIn(result: GetCredentialResponse): Boolean {
        val credential = result.credential

        if (
            credential is CustomCredential &&
            credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
        ) {

            try {

                val tokenCredential = GoogleIdTokenCredential.createFrom(credential.data)


                val authCredential = GoogleAuthProvider.getCredential(
                    tokenCredential.idToken, null
                )
                val authResult = firebaseAuth.signInWithCredential(authCredential).await()

                return authResult.user != null

            } catch (e: GoogleIdTokenParsingException) {
                println("GoogleIdTokenParsingException: ${e.message}")
                return false
            }

        } else {

            return false
        }

    }

    private suspend fun buildCredentialRequest(): GetCredentialResponse {
        val request = GetCredentialRequest.Builder()
            .addCredentialOption(
                GetGoogleIdOption.Builder()
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId(
                        LocalSecrets.WEB_CLIENT_ID
                    )
                    .setAutoSelectEnabled(false)
                    .build()
            )
            .build()

        return credentialManager.getCredential(
            request = request, context = context
        )
    }

}