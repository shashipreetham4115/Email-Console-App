import ui.AuthenticateUi
import ui.ComposeMailUi
import ui.MailUi
import ui.services.ToDoMenuServices
import ui.utils.InputUtil
import ui.utils.MenuList
import kotlin.system.exitProcess

fun main() {
    val inbox: ToDoMenuServices = MailUi("Inbox")
    val sent: ToDoMenuServices = MailUi("Sent")
    val draft: ToDoMenuServices = MailUi("Draft")
    val outbox: ToDoMenuServices = MailUi("Outbox")
    val auth: ToDoMenuServices = AuthenticateUi

    while (true) {
        auth.toDoMenu()

        when (InputUtil.getInt(MenuList.getMainMenu())) {
            1 -> inbox.toDoMenu()
            2 -> sent.toDoMenu()
            3 -> draft.toDoMenu()
            4 -> ComposeMailUi().toDoMenu()
            5 -> outbox.toDoMenu()
            6 -> AuthenticateUi.signOut()
            7 -> exitProcess(0)
        }

    }
}

