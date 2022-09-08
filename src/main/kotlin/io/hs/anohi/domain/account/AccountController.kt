package io.hs.anohi.domain.account

import io.hs.anohi.domain.account.payload.AccountDetail
import io.hs.anohi.domain.account.payload.AccountJoinForm
import io.hs.anohi.domain.account.payload.AccountSummary
import io.hs.anohi.domain.account.payload.AccountUpdateForm
import io.swagger.annotations.ApiResponses
import io.swagger.v3.oas.annotations.Operation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import javax.validation.Valid

@RestController
@RequestMapping("/v1/accounts")
class AccountController(
    @Autowired
    private val accountService: AccountService
) {
    @Operation(summary = "test hello", description = "hello api example")
    @PostMapping
    fun create(
        @RequestBody @Valid accountJoinForm: AccountJoinForm
    ): ResponseEntity<Any> {
        val accountId = accountService.create(accountJoinForm)

        val location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(accountId)
            .toUri()

        return ResponseEntity.created(location).build()
    }

    @GetMapping("/email/{email}/existence")
    fun existsEmail(@PathVariable email: String): ResponseEntity<Any> {
        accountService.existsEmail(email)
        return ResponseEntity.noContent().build()
    }

    @GetMapping
    fun getUsers(
        @RequestParam(defaultValue = "1") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): ResponseEntity<List<AccountSummary>> {

        val accountList = accountService.findAll(page, size)

        return ResponseEntity.ok(accountList)
    }

    @GetMapping("/{id}")
    fun getUserDetail(@PathVariable id: Long): ResponseEntity<AccountDetail> {
        val account = accountService.findById(id)

        return ResponseEntity.ok(account)
    }

    @PutMapping("/{id}")
    fun modifyUserInfo(
        @PathVariable id: Long,
        @Valid @RequestBody request: AccountUpdateForm
    ): ResponseEntity<Any> {

        accountService.update(id, request)
        val account = accountService.findById(id)

        return ResponseEntity.ok().build()
    }

    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: Long): ResponseEntity<Any> {

        accountService.delete(id)

        return ResponseEntity.ok().build()
    }

}