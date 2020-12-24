package salvador.thiago.bankapi.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import salvador.thiago.bankapi.entity.Account
import salvador.thiago.bankapi.repository.AccontRespository
import java.time.LocalDateTime

@RestController
@RequestMapping("/accounts")
class AccountController(private val respository: AccontRespository) {

    @PostMapping
    fun create(@RequestBody account: Account): Account = this.respository.save(account)

    @GetMapping
    fun getAll(): List<Account> = respository.findAll()

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<Account> =
        respository.findById(id).map {
            ResponseEntity.ok(it)
        }.orElse(ResponseEntity.notFound().build())

    @PutMapping("/{id}")
    fun apdate(@PathVariable id: Long, @RequestBody account: Account) : ResponseEntity<Account> =
        respository.findById(id).map {
            val accountToUpdate = it.copy(
                name = account.name,
                document = account.document,
                phone = account.phone,
                updateDate = LocalDateTime.now()

            )
            ResponseEntity.ok(respository.save(accountToUpdate))
        }.orElse(ResponseEntity.notFound().build())

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Void> =
        respository.findById(id).map {
            respository.delete(it)
            ResponseEntity<Void>(HttpStatus.OK)
        }.orElse(ResponseEntity.notFound().build())


}