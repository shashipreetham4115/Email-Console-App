package ui.utils

object MenuList {
    fun getMailListMenu(): String {
        return """
          1) Open Mail
          2) Delete Mails
          3) Mark Mails As UnRead
          4) Mark Mails As Important
          5) Mark Mails As UnImportant
          6) Go Back
          Please Choose Your Choice
       """.trimIndent()
    }

    fun getMailMenu(folder: String): String {
        return if (folder == "Draft" || folder == "Outbox") """
          1) Send
          2) Edit
          3) Delete
          4) Go Back
          Please Choose Your Choice
       """.trimIndent() else """
          1) Reply
          2) Reply All
          3) Forward
          4) Delete
          5) Go Back
          Please Choose Your Choice
       """.trimIndent()
    }

    fun getAuthMenu(): String {
        return """
                |--------Welcome to Zoho Mail---------
                |
                |1) Sign in
                |2) Sign up
                |3) Forgot Password
                |4) Exit
                |Please Enter Your Choice
            """.trimMargin("|")
    }

    fun getComposeMailMenu(): String {
        return """
            1) Send
            2) Draft
            3) Edit
            4) Discard
            Please Choose Your Choice
        """.trimIndent()
    }

    fun getMainMenu(): String {
        return """
            1) Inbox
            2) Sent 
            3) Draft
            4) Compose Mail
            5) Outbox
            6) Logout
            7) Exit
            Please Enter Your Choice
        """.trimIndent()
    }
}