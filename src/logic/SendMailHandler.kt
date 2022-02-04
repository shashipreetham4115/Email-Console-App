package logic

import entites.Mail
import logic.datahandler.MailBoxHandler
import logic.services.OutboxServices
import logic.services.SendMailServices
import java.time.LocalDateTime

class SendMailHandler : SendMailServices {
    private val outbox: OutboxServices = OutboxHandler()

    override fun send(mail: Mail) {
        if (mail.folder != "draft" && mail.folder != "outbox")
            MailBoxHandler.addMail(mail, mail.from)
        mail.folder = "outbox"
        mail.sentDate = LocalDateTime.now()
        outbox.sendQueuedMails(mail.from)
    }

}