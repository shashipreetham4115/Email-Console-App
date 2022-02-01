package logic

import entites.Mail
import logic.datahandler.MailBoxHandler
import logic.services.SendMailServices

class SendMailHandler : SendMailServices {
    private val outbox = OutboxHandler()

    override fun send(mail: Mail) {
        mail.folder = "outbox"
        MailBoxHandler.addMail(mail, mail.from)
        outbox.sendQueuedMails(mail.from)
    }

}