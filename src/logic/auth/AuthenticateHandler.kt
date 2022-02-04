package logic.auth

import entites.Profile
import entites.Role
import entites.User
import enums.ROLE
import logic.datahandler.EmailDataHandler
import logic.services.AuthenticateHandlerServices

class AuthenticateHandler : AuthenticateHandlerServices {

    override fun signIn(email: String, password: String): Profile? {
        val user = EmailDataHandler.getUser(email)
        if (isloggingInAllowed(user?.role) && user?.password == password)
            return getProfile(user)
        return null
    }

    override fun signUp(user: User): Profile {
        EmailDataHandler.addUser(user, ROLE.USER)
        return getProfile(user)
    }

    override fun forgotPassword(email: String, answer: String): Boolean {
        val user = EmailDataHandler.getUser(email)
        if (isloggingInAllowed(user?.role) && user?.securityAns == answer)
            return true
        return false
    }

    override fun getSecurityQuestion(email: String): Int {
        val user = EmailDataHandler.getUser(email)
        return user?.securityQues!!
    }

    override fun setPassword(email: String, password: String) {
        val user = EmailDataHandler.getUser(email)
        user?.password = password
    }

    override fun isEmailAvailable(email: String): String {
        return if (EmailDataHandler.getDomain(email.split("@")[1]) != null)
            if (EmailDataHandler.getUser(email) == null) "Email Available" else "Email Already Exists"
        else "Invalid Domain"
    }

    private fun getProfile(user: User): Profile {
        return Profile("${user.firstName} ${user.lastName}", user.email, user.role.roleName)
    }

    private fun isloggingInAllowed(role: Role?): Boolean {
        return role?.userPermissions?.get("loggingIn") == "Allow"
    }
}