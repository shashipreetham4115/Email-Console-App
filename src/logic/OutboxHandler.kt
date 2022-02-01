package logic

import entites.Mail
import logic.datahandler.EmailDataHandler
import logic.datahandler.MailBoxHandler
import logic.services.OutboxServices
import java.util.*

class OutboxHandler : OutboxServices {

    override fun sendQueuedMails(email: String) {
        val mailList = MailBoxHandler.getMails(email, "outbox")
        for (mail in mailList) {
            for (to in mail.to) {
                val uid = EmailDataHandler.getUserId(to)
                if (uid == null) failureHandler(mail.from, to, "Invalid Domain or Mail ID")
                else successHandler(mail, to)
            }
        }
    }

    override fun successHandler(mail: Mail, to: String) {
        val newMail = mail.copy(folder = "inbox", id = UUID.randomUUID().toString())
        val response = MailBoxHandler.addMail(newMail, to)
        if (!response) failureHandler(mail.from, to, "Internal Server Error")
        else MailBoxHandler.updateMail(mail.from, mail.id, "folder", "sent")
    }

    override fun failureHandler(from: String, to: String, reason: String) {
        val mail = Mail(
            "mailer@zoho.com",
            listOf(from),
            "Failed to Deliver Your Mail",
            "$reason \nYour message wasn't delivered to $to because of $reason",
            folder = "inbox"
        )
        MailBoxHandler.addMail(mail, from)
    }
}