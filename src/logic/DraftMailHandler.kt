package logic

import entites.Mail
import logic.datahandler.MailBoxHandler
import logic.services.DraftMailServices

class DraftMailHandler : DraftMailServices {
    override fun draft(mail: Mail) {
        mail.folder = "draft"
        MailBoxHandler.addMail(mail, mail.from)
    }
}