package logic.datahandler

import entites.Mail
import entites.MailBox

object MailBoxHandler {
    private val mailboxes = mutableMapOf<String, MailBox>()

    fun createMailBox(email: String): Boolean {
        return try {
            val address = getMailBoxAddress(email)
            val mailBox = MailBox(address)
            mailboxes[address] = mailBox
            true
        } catch (e: Exception) {
            false
        }
    }

    fun getMails(email: String, folder: String): List<Mail> {
        return getMail(email) { i -> i.folder == folder && i.headMailId == null }
    }

    fun getMail(email: String, mailId: String, folder: String): List<Mail> {
        val list = getMail(email) { i -> i.folder == folder && (i.headMailId == mailId || i.id == mailId) }
        list.forEach { mail -> mail.unRead = false }
        return list
    }

    fun addMail(mail: Mail, to: String): Boolean {
        return try {
            val mailboxAddress = getMailBoxAddress(to)
            if (mailboxAddress == null || mailboxAddress == "") return false
            val mailExist = mailboxes[mailboxAddress]?.mails?.find { i -> i.id == mail.id }
            if (mailExist == null)
                mailboxes[mailboxAddress]?.mails?.add(mail)
            else mailExist.folder = mail.folder
            println(mail)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun updateMail(email: String, mailId: String, key: String, value: Any): Boolean {
        return try {
            val mailboxAddress = getMailBoxAddress(email)
            val mail = mailboxes[mailboxAddress]?.mails?.find { i -> i.id == mailId } ?: return false
            when (key) {
                "unread" -> mail.unRead = value as Boolean
                "important" -> mail.important = value as Boolean
                "folder" -> mail.folder = value as String
                else -> return false
            }
            true
        } catch (e: Exception) {
            false
        }
    }

    private fun getMail(email: String, condition: (i: Mail) -> Boolean): List<Mail> {
        return try {
            val mailBoxAddress = getMailBoxAddress(email)
            mailboxes[mailBoxAddress]?.mails?.filter(condition) ?: listOf()
        } catch (e: Exception) {
            listOf()
        }
    }

    private fun getMailBoxAddress(email: String): String {
        val userid = EmailDataHandler.getUserId(email)
        return userid?.replace("-", "") ?: ""
    }
}