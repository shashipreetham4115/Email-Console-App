package ui.services

import entites.Mail

interface MailOperationsUiServices {
    fun reply(mail: Mail, all: Boolean)
    fun draft(mail: Mail)
    fun send(mail: Mail)
    fun markAsUnImportant(mailIds: List<String>)
    fun markAsImportant(mailIds: List<String>)
    fun markAsUnRead(mailIds: List<String>)
    fun delete(mailIds: List<String>, folder: String)
    fun fetch(mailId: String, folder: String): List<Mail>
    fun fetchFolder(folder: String): List<Mail>
    fun forward(mail: Mail)
}