package ui.utils

import ui.utils.InputUtil.getString
import java.util.regex.Pattern

object InputValidationUtil {

    fun getPassword(): String {
        val input = getString("Please Enter Your Password")
        if (input == "--q") return input
        val passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$".toRegex()
        return if (passwordRegex.matches(input)) input else {
            println(
                """
                Please Enter Valid Password
                Password should be 8 or more characters with a mix of capital letters(A-Z), small letters(a-z), numbers(0-9) & symbols(@,#,$,%,^,&,+,=)
            """.trimIndent()
            )
            getPassword()
        }
    }

    fun getEmail(request: String): String {
        var email: String = getString(request).lowercase()
        if (email == "--q") return email
        if (!email.contains("@")) email = "$email@zoho.com"
        return if (isValidEmail(email)) email else {
            println("Please Enter Valid Email ID\nSorry, only letters (a-z), numbers (0-9), and periods (.) are allowed.")
            getEmail(request)
        }
    }

    fun isValidEmail(str: String): Boolean {
        val emailPattern = Pattern.compile(
            "[a-zA-Z0-9+._%\\-]{1,50}" + "@" + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" + "(" + "\\." + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+"
        )
        return emailPattern.matcher(str).matches()
    }
}