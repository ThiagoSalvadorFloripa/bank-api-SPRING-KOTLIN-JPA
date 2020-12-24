package salvador.thiago.bankapi.service

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import salvador.thiago.bankapi.entity.Account
import salvador.thiago.bankapi.repository.AccountRespository
import java.lang.RuntimeException
import java.time.LocalDateTime
import java.util.*

@Service
class AccountServiceImpl(private val repository: AccountRespository) : AccountService {
    override fun create(account: Account): Account {
        return repository.save(account)
    }

    override fun getAll(): List<Account> {
        return repository.findAll()
    }

    override fun getById(id: Long): Optional<Account> {
        return repository.findById(id)
    }

    override fun update(id: Long, account: Account): Optional<Account> {
        val doResult = getById(id)
        if (doResult.isEmpty) Optional.empty<Account>()

        return doResult.map {
            val accountToUpdate = it.copy(
                name = account.name,
                document = account.document,
                phone = account.phone,
                updateDate = LocalDateTime.now()
            )
            repository.save(accountToUpdate)
        }

    }

    override fun delete(id: Long) {
        repository.findById(id).map {
            repository.delete(it)
            ResponseEntity<Void>(HttpStatus.OK)
        }.orElseThrow{ throw RuntimeException("id not found $id")}
    }
}