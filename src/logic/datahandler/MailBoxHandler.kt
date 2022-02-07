package logic.datahandler

import entites.Mail
import entites.MailBox
import java.io.File

object MailBoxHandler {
    private val mailboxes = mutableMapOf<String, MailBox>()

    fun createMailBox(email: String): Boolean {
        return try {
            val address = getMailBoxAddress(email)
            val mailBox = MailBox(address)
            mailboxes[address] = mailBox
            mailBox.mails[0].to = listOf(email)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun getMails(email: String, folder: String): List<Mail> {
        return getMail(email) { i -> i.folder == folder || (folder == "important" && i.folder == "inbox" && i.important) }
    }

    fun getMail(email: String, mailId: String, folder: String): List<Mail> {
        val list =
            getMail(email) { i ->
                if (folder == "inbox" || folder == "important") (i.folder == "inbox" || i.folder == "sent") && i.headMailId == mailId
                else i.folder == folder && i.id == mailId
            }
        list.forEach { mail -> mail.unRead = false }
        return list
    }

    fun addMail(mail: Mail, to: String): Boolean {
        return try {
            val mailboxAddress = getMailBoxAddress(to)
            if (mailboxAddress == "") return false
            mailboxes[mailboxAddress]?.mails?.add(mail)
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
        val myFile = File("debug.txt")
        myFile.printWriter().use { out ->
            mailboxes.forEach { i ->
                run {
                    out.println("\n" + i.key + "\n")
                    i.value.mails.forEach { out.println("$it\n") }
                }
            }
        }
        val userid = EmailDataHandler.getUserId(email)
        return userid?.replace("-", "") ?: ""
    }
}