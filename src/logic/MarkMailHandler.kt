package logic

import logic.datahandler.MailBoxHandler
import logic.services.MarkMailServices

class MarkMailHandler : MarkMailServices {
    override fun markUnread(email: String, mailIds: List<String>) {
        mailIds.forEach { id -> MailBoxHandler.updateMail(email, id, "unread", true) }
    }

    override fun markImportant(email: String, mailIds: List<String>) {
        mailIds.forEach { id -> MailBoxHandler.updateMail(email, id, "important", true) }
    }

    override fun markUnImportant(email: String, mailIds: List<String>) {
        mailIds.forEach { id -> MailBoxHandler.updateMail(email, id, "important", false) }
    }
}