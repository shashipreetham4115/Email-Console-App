package ui

import entites.Mail
import logic.*
import ui.services.MailOperationsUiServices

class MailOperationsUi : MailOperationsUiServices {
    private val fetch = FetchMailHandler()
    private val delete = DeleteMailHandler()
    private val operations = MarkMailHandler()
    private val send = SendMailHandler()
    private val draft = DraftMailHandler()

    override fun fetchFolder(folder: String): List<Mail> {
        return fetch.fetchFolder(AuthenticateUi.loggedInUser?.email!!, folder)
    }

    override fun fetch(mailId: String, folder: String): List<Mail> {
        return fetch.fetch(AuthenticateUi.loggedInUser?.email!!, mailId, folder)
    }

    override fun delete(mailIds: List<String>) {
        delete.delete(AuthenticateUi.loggedInUser?.email!!, mailIds)
    }

    override fun markAsUnRead(mailIds: List<String>) {
        operations.markUnread(AuthenticateUi.loggedInUser?.email!!, mailIds)
    }

    override fun markAsImportant(mailIds: List<String>) {
        operations.markImportant(AuthenticateUi.loggedInUser?.email!!, mailIds)
    }

    override fun markAsUnImportant(mailIds: List<String>) {
        operations.markUnImportant(AuthenticateUi.loggedInUser?.email!!, mailIds)
    }

    override fun send(mail: Mail) {
        send.send(mail)
    }

    override fun draft(mail: Mail) {
        draft.draft(mail)
    }

    override fun reply(mail: Mail, all: Boolean) {
        val newMail =
            Mail(AuthenticateUi.loggedInUser?.email!!, mail.to, mail.subject, "", if (all) mail.CC else listOf(), if (all) mail.BCC else listOf())
        newMail.headMailId = mail.headMailId ?: mail.id
        mail.childMailId = newMail.id
        println(newMail)
        ComposeMailUi(newMail).toDoMenu()
    }
}