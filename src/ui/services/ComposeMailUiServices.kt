package ui.services

import entites.Mail

interface ComposeMailUiServices {
    fun composeMail(): Mail?
    fun editMail(): Mail
}