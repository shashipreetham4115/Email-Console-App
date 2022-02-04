package ui

import entites.Mail
import ui.services.MailOperationsUiServices
import ui.services.MailUiServices
import ui.services.ToDoMenuServices
import ui.utils.FileWritter
import ui.utils.FormatterUtils
import ui.utils.InputUtil
import ui.utils.MenuList

class MailUi(val folder: String) : ToDoMenuServices, MailUiServices {
    private val operations: MailOperationsUiServices = MailOperationsUi()
    private var mailList = listOf<Mail>()

    override fun toDoMenu() {
        while (true) {
            if (!displayMails()) return
            when (val i = InputUtil.getInt(MenuList.getMailListMenu())) {
                -1, 6 -> return
                1 -> displayMail()
                2, 3, 4, 5 -> callToDoFuncs(i)
                else -> println("Please Enter Valid Option")
            }
        }
    }

    override fun displayMails(): Boolean {
        mailList = operations.fetchFolder(folder.lowercase())
        val strFormatter = "%1\$-10s%2\$-30s%3\$-35s%4\$-20s%5\$-10s"
        FileWritter.myFile.printWriter().use { out ->
            out.println("\n------------------------------------------------${folder.uppercase()}---------------------------------------------------")
            if (mailList.isEmpty()) {
                out.println("$folder is Empty")
                out.println("--------------------------------------------------------------------------------------------------------")
                return false
            }
            out.println("--------------------------------------------------------------------------------------------------------")
            out.println(
                String.format(
                    strFormatter,
                    "S.No",
                    if (folder == "Inbox") "From" else "To",
                    "Subject",
                    "Date",
                    if (folder == "Inbox") "Status" else ""
                )
            )
            out.println("--------------------------------------------------------------------------------------------------------")
            for ((sno, i) in mailList.withIndex()) {
                val to = i.to.joinToString(",")
                val toFor = if (to.length > 25) to.substring(0..25) + "..." else to
                val fromFor = if (i.from.length > 25) i.from.substring(0..25) + "..." else i.from
                val subFor = if (i.subject.length > 25) i.subject.substring(0..25) + "..." else i.subject
                out.println(
                    String.format(
                        strFormatter,
                        sno + 1,
                        if (folder == "Inbox") fromFor else toFor,
                        subFor,
                        FormatterUtils.formatDate(i.sentDate),
                        if (folder == "Inbox") if (i.unRead) "Unread" else "Read" else ""
                    )
                )
            }
            out.println("--------------------------------------------------------------------------------------------------------")
        }
        return true
    }

    override fun displayMailDoMenu(mail: Mail) {
        val compose: ToDoMenuServices = ComposeMailUi(mail)
        while (true) {
            val input = InputUtil.getInt(MenuList.getMailMenu(folder))
            val options = arrayOf("--q", "") + MenuList.getMailMenu(folder).split("\n")
            when (val i = options[input + 1]) {
                "--q", "5) Go Back", "4) Go Back" -> return
                "1) Reply", "2) Reply All" -> return operations.reply(mail, i == "2) Reply All")
                "2) Edit" -> return compose.toDoMenu()
                "1) Send" -> return operations.send(mail)
                "3) Forward" -> return operations.forward(mail)
                "4) Delete", "3) Delete" -> return operations.delete(listOf(mail.id))
                else -> println("Please Enter Valid Option")
            }
        }
    }

    override fun displayMail() {
        val i = InputUtil.getInt("Please Enter S.No of Mail You Want to Display")
        if (i == -1) return
        val mId = if (folder == "Inbox") mailList[i - 1].headMailId else mailList[i - 1].id
        val mailThread = operations.fetch(mId, folder.lowercase())
        FileWritter.myFile.printWriter().use { out ->
            for ((sno, i) in mailThread.withIndex()) {
                out.println(
                    """
                |
                |--------------------------------------------------------------------------------------------------------
                |S.No    : ${String.format("%1\$-80s%2\$-5s", sno + 1, i.folder.uppercase())}
                |Subject : ${i.subject}
                |From    : ${i.from}
                |To      : ${i.to.joinToString(";")}
                |Date    : ${FormatterUtils.formatDate(i.sentDate)}
                |
                |${i.body}
                |--------------------------------------------------------------------------------------------------------
                |                                                   ↑
            """.trimMargin("|")
                )
            }
        }
        displayMailDoMenu(mailThread[0])
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
}