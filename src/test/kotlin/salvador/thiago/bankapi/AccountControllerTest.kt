package salvador.thiago.bankapi

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import salvador.thiago.bankapi.entity.Account
import salvador.thiago.bankapi.repository.AccontRespository
import java.time.LocalDateTime

@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerTest {

    @Autowired lateinit var mockMvc: MockMvc
    @Autowired lateinit var accountRepository: AccontRespository

    @Test
    fun `test find all`(){
        accountRepository.save(
            Account(name = "Test", document = "123", phone = "987654321", creationDate = LocalDateTime.parse("2020-12-24T15:26:57.286358"), updateDate = null))

        mockMvc.perform(MockMvcRequestBuilders.get("/accounts"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("\$").isArray)
            .andExpect(MockMvcResultMatchers.jsonPath("\$[0].id").isNumber)
            .andExpect(MockMvcResultMatchers.jsonPath("\$[0].name").isString)
            .andExpect(MockMvcResultMatchers.jsonPath("\$[0].document").isString)
            .andExpect(MockMvcResultMatchers.jsonPath("\$[0].phone").isString)
            .andExpect(MockMvcResultMatchers.jsonPath("\$[0].creationDate").isString)
            .andExpect(MockMvcResultMatchers.jsonPath("\$[0].updateDate").isEmpty)
            .andDo(MockMvcResultHandlers.print())

    }

    @Test
    fun `test find by id`() {
        val account = accountRepository.save(
            Account(name = "Test", document = "123", phone = "987654321", creationDate = LocalDateTime.parse("2020-12-24T15:26:57.286358"), updateDate = null))

        mockMvc.perform(MockMvcRequestBuilders.get("/accounts/${account.id}"))

            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("\$.id").value(account.id))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.name").value(account.name))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.document").value(account.document))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.phone").value(account.phone))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.creationDate").value("2020-12-24T15:26:57.286358"))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.updateDate").value(account.updateDate))
            .andDo(MockMvcResultHandlers.print())
    }

//    @Test
//    fun `test create account`() {
//        val account = accountRepository.save(
//            Account(name = "Test", document = "123", phone = "987654321"))
//
//        val json = ObjectMapper().writeValueAsString(account)
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/accounts")
//            .accept(MediaType.APPLICATION_JSON)
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(json))
//            .andExpect(MockMvcResultMatchers.status().isCreated)
////            .andExpect(MockMvcResultMatchers.jsonPath("\$.name").value(account.name))
////            .andExpect(MockMvcResultMatchers.jsonPath("\$.document").value(account.document))
////            .andExpect(MockMvcResultMatchers.jsonPath("\$.phone").value(account.phone))
//            //.andExpect(MockMvcResultMatchers.jsonPath("\$.creationDate").value("2020-12-24T15:26:57.286358"))
//            //.andExpect(MockMvcResultMatchers.jsonPath("\$.updateDate").value(account.updateDate))
//            .andDo(MockMvcResultHandlers.print())
//
//       // Assertions.assertFalse(accountRepository.findAll().isEmpty())
//    }
//
//    @Test
//    fun `test update account`() {
//        val account = accountRepository
//            .save(Account(name = "Test", document = "123", phone = "987654321"))
//            .copy(name = "Updated")
//        val json = ObjectMapper().writeValueAsString(account)
//        mockMvc.perform(MockMvcRequestBuilders.put("/accounts/${account.id}")
//            .accept(MediaType.APPLICATION_JSON)
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(json))
//            .andExpect(MockMvcResultMatchers.status().isOk)
//            .andExpect(MockMvcResultMatchers.jsonPath("\$.name").value(account.name))
//            .andExpect(MockMvcResultMatchers.jsonPath("\$.document").value(account.document))
//            .andExpect(MockMvcResultMatchers.jsonPath("\$.phone").value(account.phone))
//            .andDo(MockMvcResultHandlers.print())
//
//        val findById = accountRepository.findById(account.id!!)
//        Assertions.assertTrue(findById.isPresent)
//        Assertions.assertEquals(account.name, findById.get().name)
//    }

    @Test
    fun `test delete account`() {
        val account = accountRepository
            .save(Account(name = "Test", document = "123", phone = "987654321"))
        mockMvc.perform(MockMvcRequestBuilders.delete("/accounts/${account.id}"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andDo(MockMvcResultHandlers.print())

        val findById = accountRepository.findById(account.id!!)
        Assertions.assertFalse(findById.isPresent)
    }
}