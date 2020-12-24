package salvador.thiago.bankapi.entity

import java.time.LocalDate
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity(name = "account")
data class Account (

    @Id @GeneratedValue
    val id: Long? = null,
    val name: String,
    val document: String,
    val phone: String,
    val creationDate: LocalDateTime? =LocalDateTime.now(),
    var updateDate: LocalDateTime? = null
)