package logic

import logic.datahandler.MailBoxHandler
import logic.services.DeleteMailServices

class DeleteMailHandler : DeleteMailServices {
    override fun delete(email: String, mailIds: List<String>) {
        mailIds.forEach { id -> MailBoxHandler.updateMail(email, id, "folder", "trash") }
    }
}