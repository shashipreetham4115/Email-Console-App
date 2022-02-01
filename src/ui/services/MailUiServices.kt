package ui.services

import entites.Mail

interface MailUiServices {
    fun displayMails(): Boolean
    fun displayMail()
    fun displayMailDoMenu(mail: Mail)
}