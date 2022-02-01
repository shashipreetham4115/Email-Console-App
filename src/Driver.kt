import ui.AuthenticateUi
import ui.ComposeMailUi
import ui.MailUi
import ui.utils.InputUtil
import kotlin.system.exitProcess

fun main() {
    val inbox = MailUi("Inbox")
    val sent = MailUi("Sent")
    val draft = MailUi("Draft")

    while (true) {
        if (AuthenticateUi.loggedInUser == null) {
            AuthenticateUi.toDoMenu()
        }
        val request = """
            1) Inbox
            2) Sent 
            3) Draft
            4) Compose Mail
            5) Logout
            6) Exit
            Please Enter Your Choice
        """.trimIndent()
        when (InputUtil.getInt(request)) {
            1 -> inbox.toDoMenu()
            2 -> sent.toDoMenu()
            3 -> draft.toDoMenu()
            4 -> ComposeMailUi().toDoMenu()
            5 -> AuthenticateUi.signOut()
            6 -> exitProcess(0)
        }
    }
}