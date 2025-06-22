package com.cloudsurfe.jellienotes.modules.auth.state

class PasswordState : TextFieldState(
    validator = ::isPasswordValid, errorFor = ::passwordValidationError
)

class ConfirmPasswordState(private val passwordState: PasswordState) : TextFieldState() {
    override val isValid: Boolean
        get() = passwordAndConfirmationValid(
            password = passwordState.text,
            confirmedPassword = text
        )

    override fun getError(): String? {
        return if (showErrors()) {
            passwordConfirmationError()
        } else {
            null
        }
    }
}

private fun passwordAndConfirmationValid(password: String, confirmedPassword: String): Boolean {
    return isPasswordValid(password)
}

private fun isPasswordValid(password: String): Boolean {
    return password.length > 3
}

private fun passwordValidationError(password: String): String {
    return "Invalid password"
}

private fun passwordConfirmationError(): String {
    return "Password don't match"
}


