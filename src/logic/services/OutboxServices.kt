package logic.services

import entites.Mail

interface OutboxServices {
    fun sendQueuedMails(email: String)
    fun successHandler(mail: Mail, to: String)
    fun failureHandler(from: String, to: String, reason: String)
}