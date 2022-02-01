package logic.services

import entites.Mail

interface DraftMailServices {
    fun draft(mail: Mail)
}