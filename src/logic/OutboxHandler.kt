package logic

import entites.Mail
import logic.datahandler.EmailDataHandler
import logic.datahandler.MailBoxHandler
import logic.services.OutboxServices
import java.util.*

class OutboxHandler : OutboxServices {

    override fun sendQueuedMails(email: String) {
        try {
            val mailList = MailBoxHandler.getMails(email, "outbox")
            for (mail in mailList) {
                for (to in mail.to)
                    mailer(to, mail)
                for (cc in mail.CC)
                    if (!mail.to.contains(cc))
                        mailer(cc, mail)
                for (bcc in mail.BCC)
                    if (!mail.to.contains(bcc) && !mail.CC.contains(bcc))
                        mailer(bcc, mail)
            }
        } catch (e: Exception) {
        }
    }

    override fun successHandler(mail: Mail, to: String) {
        try {
            val newMail = mail.copy(folder = "inbox", id = UUID.randomUUID().toString())
            val response = MailBoxHandler.addMail(newMail, to)
            if (!response) failureHandler(mail.from, to, "Internal Server Error")
            else MailBoxHandler.updateMail(mail.from, mail.id, "folder", "sent")
        } catch (e: Exception) {
        }
    }

    override fun failureHandler(from: String, to: String, reason: String) {
        try {
            val mail = Mail(
                "mailer@zoho.com",
                listOf(from),
                "Failed to Deliver Your Mail",
                "$reason \nYour message wasn't delivered to $to because of $reason",
                folder = "inbox"
            )
            MailBoxHandler.addMail(mail, from)
        } catch (e: Exception) {
        }
    }

    private fun mailer(email: String, mail: Mail) {
        val uid = EmailDataHandler.getUserId(email)
        if (uid == null) failureHandler(mail.from, email, "Invalid Domain or Mail ID")
        else successHandler(mail, email)
    }
}