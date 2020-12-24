package salvador.thiago.bankapi.repository

import org.springframework.data.jpa.repository.JpaRepository
import salvador.thiago.bankapi.entity.Account

interface AccountRespository : JpaRepository<Account,Long> {
}