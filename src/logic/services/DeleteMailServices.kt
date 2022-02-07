package logic.services

import entites.Mail

interface DeleteMailServices {
    fun delete(email: String, mailIds: List<String>, folder: String)
}