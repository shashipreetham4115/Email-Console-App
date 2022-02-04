package entites

data class MailBox(
    val address: String,
    val mails: MutableList<Mail> = mutableListOf<Mail>(
        Mail(
            "welcome@zoho.com", listOf(), "Welcome to Zoho Mail",
            """
               |Hello,
               |
               |Welcome to Zoho Mail! The 'forever ad-free' email service, for all your business and personal email.
               |We created Zoho Mail to help people and businesses work smarter and take control of their inbox. 
               |Thanks for choosing Zoho Mail! We’re glad to have you with us!
               |
               |Thanks and Regards,
               |Zoho Mail Team.
               |""".trimMargin("|"),
            folder = "inbox",
        )
    )
)