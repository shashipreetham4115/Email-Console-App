package logic.services

import entites.Mail

interface MarkMailServices {
    fun markUnread(email: String, mailIds: List<String>)
    fun markImportant(email: String, mailIds: List<String>)
    fun markUnImportant(email: String, mailIds: List<String>)
}