package logic.services

import entites.Profile
import entites.User

interface AuthenticateHandlerServices {
    fun signIn(email: String, password: String): Profile?
    fun signUp(user: User): Profile
    fun forgotPassword(email: String, answer: String): Boolean
    fun getSecurityQuestion(email: String): Int
    fun setPassword(email: String, password: String)
    fun isEmailAvailable(email: String): String
}