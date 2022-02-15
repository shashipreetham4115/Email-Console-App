package ui

import entites.Mail
import logic.services.*
import ui.services.MailOperationsUiServices
import ui.utils.FormatterUtils

class MailOperationsUi(
    private val fetch: FetchMailServices,
    private val delete: DeleteMailServices,
    private val send: SendMailServices,
    private val draft: DraftMailServices,
    private val operations: MarkMailServices,
) : MailOperationsUiServices {

    override fun fetchFolder(folder: String): List<Mail> {
        return fetch.fetchFolder(AuthenticateUi.loggedInUser?.email!!, folder)
    }

    override fun fetch(mailId: String, folder: String): List<Mail> {
        return fetch.fetch(AuthenticateUi.loggedInUser?.email!!, mailId, folder)
    }

    override fun delete(mailIds: List<String>, folder: String) {
        delete.delete(AuthenticateUi.loggedInUser?.email!!, mailIds, folder)
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
        val email = AuthenticateUi.loggedInUser?.email!!
        val to = mail.to.map { i -> if (i == email) mail.from else i }
        val replyMessage = """
            |
            |
            |---- On ${FormatterUtils.formatDate(mail.sentDate)} ${mail.from} wrote ----
            |   
            |   ${mail.body.replace("\n", "\n|    ")}
        """.trimMargin("|")
        val newMail =
            Mail(
                email,
                to,
                mail.subject,
                replyMessage.trimEnd(),
                if (all) mail.CC else listOf(),
                if (all) mail.BCC else listOf()
            )
        newMail.headMailId = mail.headMailId
        mail.childMailId = newMail.id
        ComposeMailUi(newMail).toDoMenu()
    }

    override fun forward(mail: Mail) {
        val subject = if (mail.subject.substring(0..3) == "Fwd:") mail.subject else "Fwd: ${mail.subject}"
        val fwdMessage = """
           |
           |
           |============ Forwarded message ============
           |From    : ${mail.from}
           |To      : ${mail.to.joinToString(", ")}
           |Date    : ${FormatterUtils.formatDate(mail.sentDate)}
           |Subject : ${mail.subject}
           |============ Forwarded message ============
           |  
           |   ${mail.body.replace("\n", "\n|    ")}
        """.trimMargin("|")
        val newMail = Mail(AuthenticateUi.loggedInUser?.email!!, subject = subject, body = fwdMessage.trimEnd())
        ComposeMailUi(newMail).toDoMenu()
    }
}