package logic.services

import entites.Mail

interface SendMailServices {
    fun send(mail: Mail)
}