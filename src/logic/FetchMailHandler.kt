package logic

import entites.Mail
import logic.datahandler.MailBoxHandler
import logic.services.FetchMailServices

class FetchMailHandler : FetchMailServices {

    override fun fetchFolder(email: String, folder: String): List<Mail> {
        return MailBoxHandler.getMails(email, folder).reversed()
    }

    override fun fetch(email: String, mailId: String, folder: String): List<Mail> {
        return MailBoxHandler.getMail(email, mailId, folder).reversed()
    }
}