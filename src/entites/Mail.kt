package entites

import java.time.LocalDateTime
import java.util.*

data class Mail(
    val from: String,
    var to: List<String> = listOf(),
    var subject: String,
    var body: String,
    var CC: List<String> = listOf(),
    var BCC: List<String> = listOf(),
    var folder: String = "",
    val id: String = UUID.randomUUID().toString(),
    var childMailId: String? = null,
    var headMailId: String = UUID.randomUUID().toString(),
    var unRead: Boolean = true,
    var important: Boolean = false,
    var sentDate: LocalDateTime = LocalDateTime.now()
)