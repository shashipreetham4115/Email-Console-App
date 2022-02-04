package ui

import entites.Mail
import ui.services.ComposeMailUiServices
import ui.services.MailOperationsUiServices
import ui.services.ToDoMenuServices
import ui.utils.*

class ComposeMailUi(var mail: Mail? = null) : ToDoMenuServices, ComposeMailUiServices {
    private val operationsUi: MailOperationsUiServices = MailOperationsUi()

    override fun toDoMenu() {
        mail = if (mail == null) composeMail() else editMail()
        while (mail != null) {
            when (InputUtil.getInt(MenuList.getComposeMailMenu())) {
                -1, 4 -> return
                1 -> return operationsUi.send(mail!!)
                2 -> return operationsUi.draft(mail!!)
                3 -> mail = editMail()
                else -> print("Please Enter Valid Choice")
            }
        }
    }

    override fun composeMail(): Mail? {
        println(
            """
            Note:
            1) Please use semicolon(;) to enter multiple mails in to,cc,bcc
            2) Please use --q to exit from body or to go back
            3) CC and BCC is Optional
        """.trimIndent()
        )
        val to = getToAddress() ?: return null
        val cc = removeInvalidMails(InputUtil.getString("CC"), "CC") ?: return null
        val bcc = removeInvalidMails(InputUtil.getString("BCC"), "BCC") ?: return null
        val subject = InputUtil.getString("Subject")
        if (subject == "--q") return null
        val body = FormatterUtils.formatString(InputUtil.getMultiLineInput("Body"))
        return Mail(AuthenticateUi.loggedInUser?.email!!, to, subject, body, cc, bcc)
    }

    private fun getToAddress(): List<String>? {
        while (true) {
            val emails = InputUtil.getString("To")
            val response = removeInvalidMails(emails, "To") ?: return null
            if (response.isNotEmpty()) return response
            println("Please Enter Valid TO Address")
        }
    }

    private fun removeInvalidMails(str: String, to: String): List<String>? {
        if (str == "--q") return null
        val list = mutableListOf<String>()
        var invalidStr = mutableListOf<String>()
        for (mail in str.replace(" ", "").split(";")) {
            if (InputValidationUtil.isValidEmail(mail) && !list.contains(mail)) list.add(mail)
            else invalidStr.add(mail)
        }
        if (invalidStr.size > 1) println("${invalidStr.joinToString(", ")} these mails are invalid and removed from $to ")
        return list.toList()
    }

    override fun editMail(): Mail {
        var to: List<String>
        println("\u001B[31m" + "\nPlease Give Input From File And Click Enter After Giving Input" + "\u001B[0m")
        while (true) {
            val temp = FileWritter.getFileInput(mail?.to?.joinToString(";") ?: "", "", "To")
            val response = removeInvalidMails(temp, "To") ?: listOf()
            if (response.isNotEmpty()) {
                to = response
                break
            }
            println("Please Enter Valid TO Address")
        }
        val cc =
            removeInvalidMails(FileWritter.getFileInput(mail?.CC?.joinToString("") ?: "", "", "CC"), "CC") ?: listOf()
        val bcc = removeInvalidMails(FileWritter.getFileInput(mail?.BCC?.joinToString("") ?: "", "", "BCC"), "BCC")
            ?: listOf()
        val subject = FileWritter.getFileInput(mail?.subject ?: "", "", "Subject")
        val body = FormatterUtils.formatString(FileWritter.getFileInput(mail?.body ?: "", "\n", "Body"))
        val newMail: Mail = mail!!
        newMail.to = to
        newMail.CC = cc
        newMail.BCC = bcc
        newMail.subject = subject
        newMail.body = body
        return newMail
    }
}