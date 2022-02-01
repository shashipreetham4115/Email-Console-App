package ui.utils

import ui.utils.InputUtil.getString
import java.util.regex.Pattern

object InputValidationUtil {

    fun getEmail(request: String): String {
        var email: String = getString(request)
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