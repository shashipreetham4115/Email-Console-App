package logic

import logic.datahandler.MailBoxHandler
import logic.services.MarkMailServices

class MarkMailHandler : MarkMailServices {
    override fun markUnread(email: String, mailIds: List<String>) {
        update(email, mailIds, "unread", true)
    }

    override fun markImportant(email: String, mailIds: List<String>) {
        update(email, mailIds, "important", true)
    }

    override fun markUnImportant(email: String, mailIds: List<String>) {
        update(email, mailIds, "important", false)
    }


    private fun update(email: String, mailIds: List<String>, key: String, value: Boolean) {
        mailIds.forEach { id ->
            MailBoxHandler.updateMail(email, id, key, value)
        }
    }
}
