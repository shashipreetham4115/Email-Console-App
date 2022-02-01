package ui

import entites.Mail
import ui.services.MailUiServices
import ui.services.ToDoMenuServices
import ui.utils.FileWritter
import ui.utils.FormatterUtils
import ui.utils.InputUtil

class MailUi(val folder: String) : ToDoMenuServices, MailUiServices {
    private val operations = MailOperationsUi()
    private var mailList = listOf<Mail>()

    override fun toDoMenu() {
        val request = """
          1) Open Mail
          2) Delete Mails
          3) Mark Mails As UnRead
          4) Mark Mails As Important
          5) Mark Mails As UnImportant
          6) Go Back
          Please Choose Your Choice
       """.trimIndent()
        while (true) {
            if (!displayMails()) return
            when (val i = InputUtil.getInt(request)) {
                -1, 6 -> return
                1 -> displayMail()
                2, 3, 4, 5 -> callToDoFuncs(i)
                else -> println("Please Enter Valid Option")
            }
        }
    }

    private fun callToDoFuncs(i: Int) {
        val mailSnoStr =
            InputUtil.getString("Please Enter Mail S.No, You Can Enter Multiple S.No's by Comma(,) Separator")
        if (mailSnoStr == "--q") return
        val mailIds = getMailIds(mailSnoStr)
        when (i) {
            2 -> operations.delete(mailIds)
            3 -> operations.markAsUnRead(mailIds)
            4 -> operations.markAsImportant(mailIds)
            5 -> operations.markAsUnImportant(mailIds)
        }
    }

    private fun getMailIds(mailSnoStr: String): List<String> {
        val mailSnoArr = mailSnoStr.replace(" ", "").split(",")
        val mailIds = mutableListOf<String>()
        for (i in mailSnoArr) {
            try {
                mailIds.add(mailList[i.toInt() - 1].id)
            } catch (e: Exception) {
            }
        }
        return mailIds.toList()
    }

    override fun displayMails(): Boolean {
        mailList = operations.fetchFolder(folder.lowercase())
        val strFormatter = "%1\$-10s%2\$-30s%3\$-35s%4\$-12s%5\$-10s%6\$-10s"
        FileWritter.myFile.printWriter().use { out ->
            out.println("\n------------------------------------------------${folder.uppercase()}---------------------------------------------------")
            if (mailList.isEmpty()) {
                out.println("$folder is Empty")
                out.println("--------------------------------------------------------------------------------------------------------")
                return false
            }
            out.println("--------------------------------------------------------------------------------------------------------")
            out.println(String.format(strFormatter, "S.No", "From", "Subject", "Date", "Time", "Status"))
            out.println("--------------------------------------------------------------------------------------------------------")
            for ((sno, i) in mailList.withIndex()) {
                out.println(
                    String.format(
                        strFormatter,
                        sno + 1,
                        i.from,
                        i.subject,
                        FormatterUtils.formatDate(i.sentDate),
                        FormatterUtils.formatTime(i.sentTime),
                        if (i.unRead) "Unread" else "Read"
                    )
                )
            }
            out.println("--------------------------------------------------------------------------------------------------------")
        }
        return true
    }

    override fun displayMailDoMenu(mail: Mail) {
        val request = """
          ${if (folder == "Draft") "" else "1) Reply"}
          ${if (folder == "Draft") "" else "2) Reply All"}
          ${if (folder == "Draft") "3) Edit" else "3) Forward"}
          4) Delete
          6) Go Back
          Please Choose Your Choice
       """.trimIndent()
        val compose = ComposeMailUi(mail)
        while (true) {
            when (val i = InputUtil.getInt(request)) {
                -1, 6 -> return
                1 -> return operations.reply(mail, false)
                2 -> return operations.reply(mail, true)
                3 -> return compose.toDoMenu()
                4 -> return operations.delete(listOf(mail.id))
                else -> println("Please Enter Valid Option")
            }
        }
    }

    override fun displayMail() {
        val i = InputUtil.getInt("Please Enter S.No of Mail You Want to Display")
        if (i == -1) return
        val mailThread = operations.fetch(mailList[i - 1].id, folder.lowercase())
        FileWritter.myFile.printWriter().use { out ->
            for ((sno, i) in mailThread.withIndex()) {
                out.println(
                    """
                |
                |--------------------------------------------------------------------------------------------------------
                |S.No    : ${sno + 1}
                |Subject : ${i.subject}
                |From    : ${i.from}
                |To      : ${if (i.to.isNotEmpty()) i.to.joinToString(";") else AuthenticateUi.loggedInUser?.email}
                |Date    : ${FormatterUtils.formatDate(i.sentDate)} ${FormatterUtils.formatTime(i.sentTime)} 
                |
                |${FormatterUtils.formatString(i.body)}
                |--------------------------------------------------------------------------------------------------------
                |                                                   ↑
            """.trimMargin("|")
                )
            }
        }
        displayMailDoMenu(mailThread[0])
    }
}