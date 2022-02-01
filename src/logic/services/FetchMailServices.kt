package logic.services

import entites.Mail

interface FetchMailServices {
    fun fetch(email: String, mailId: String, folder: String): List<Mail>
    fun fetchFolder(email: String, folder: String): List<Mail>
}