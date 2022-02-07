package ui.utils

object MenuList {
    private const val statement = "\nPlease choose your choice"

    fun getMailListMenu(folder: String): String {
        val open = "1) Open mail\n2) Delete mails\n3) Go Back"
        val unread = "\n4) Mark mails as unread"
        val important = "\n6) Mark as important"
        val notImportant = "\n5) Mark as not important"
        return when (folder) {
            "Inbox" -> open + unread + notImportant + important + statement
            "Important" -> open + unread + notImportant + statement
            else -> open + statement
        }
    }

    fun getMailMenu(folder: String, important: Boolean): String {
        val draft = "1) Send\n2) Edit\n3) Delete\n4) Go Back"
        val reply = "1) Reply\n2) Reply All\n3) Forward\n4) Delete\n5) Go Back"
        val mark = "\n6) Mark as ${if (important) "not " else ""}important\n7) Mark as unread"
        return when (folder) {
            "Inbox", "Important" -> reply + mark + statement
            "Draft", "Outbox" -> draft + statement
            else -> reply + statement
        }
    }

    fun getAuthMenu(): String {
        return """
                |1) Sign in
                |2) Sign up
                |3) Forgot Password
                |4) Exit
                |Please enter your choice
            """.trimMargin("|")
    }

    fun getComposeMailMenu(): String {
        return """
            1) Send
            2) Draft
            3) Edit
            4) Discard
            Please choose your choice
        """.trimIndent()
    }

    fun getMainMenu(): String {
        return """
            1) Inbox
            2) Sent 
            3) Draft
            4) Compose Mail
            5) Outbox
            6) Important
            7) Logout
            8) Exit
            Please enter your choice
        """.trimIndent()
    }
}