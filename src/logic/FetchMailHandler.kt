package logic

import entites.Mail
import logic.datahandler.MailBoxHandler
import logic.services.FetchMailServices

class FetchMailHandler : FetchMailServices {

    override fun fetchFolder(email: String, folder: String): List<Mail> {
        val list = MailBoxHandler.getMails(email, folder).reversed()
        val checkList = mutableSetOf<String>()
        if (folder == "inbox") return list.filter { i ->
            if (checkList.contains(i.headMailId)) false else checkList.add(i.headMailId)
        }
        return list
    }

    override fun fetch(email: String, mailId: String, folder: String): List<Mail> {
        return MailBoxHandler.getMail(email, mailId, folder).reversed()
    }
}