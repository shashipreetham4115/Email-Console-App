package entites

import java.time.LocalDate
import java.time.LocalTime
import java.util.*

data class Mail(
    val from: String,
    var to: List<String> = listOf(),
    var subject: String,
    var body: String,
    var CC: List<String> = listOf(),
    var BCC: List<String> = listOf(),
    var folder: String = "outbox",
    val id: String = UUID.randomUUID().toString(),
    var childMailId: String? = null,
    var headMailId: String? = null,
    var unRead: Boolean = true,
    var important: Boolean = false,
    val sentDate: LocalDate = LocalDate.now(),
    val sentTime: LocalTime = LocalTime.now(),
)