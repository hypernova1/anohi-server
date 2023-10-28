package io.hs.anohi.domain.account

import io.hs.anohi.domain.account.payload.AccountDetail
import io.hs.anohi.domain.account.payload.AccountJoinForm
import io.hs.anohi.domain.account.payload.AccountSummary
import io.hs.anohi.infra.security.AuthAccount
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder


@Api(tags = ["계정"])
@RestController
@RequestMapping("/v1/users")
class AccountController(
    @Autowired
    private val accountService: AccountService
) {
    @ApiOperation("계정 생성")
    @PostMapping
    fun create(
        @RequestBody joinForm: AccountJoinForm
    ): ResponseEntity<Any> {
        val account = accountService.create(joinForm.token)

        val location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(account.id)
            .toUri()

        return ResponseEntity.created(location).build()
    }

    @ApiOperation("계정 목록 조회")
    @GetMapping
    fun getUsers(
        @RequestParam(defaultValue = "1") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): ResponseEntity<List<AccountSummary>> {

        val accountList = accountService.findAll(page, size)

        val accountSummaries = accountList.map { AccountSummary(it.id, it.email, it.name) }

        return ResponseEntity.ok(accountSummaries)
    }

    @ApiOperation("유저 본인 정보 조회")
    @GetMapping("/me")
    fun getUserMe(@AuthAccount account: Account): ResponseEntity<AccountDetail> {
        val profile = accountService.findById(account.id)
        return ResponseEntity.ok(profile)
    }

    @ApiOperation("유저 상세 정보 조회")
    @GetMapping("/{id}")
    fun getUserDetail(@PathVariable id: Long): ResponseEntity<AccountDetail> {
        val account = accountService.findById(id, true)
        return ResponseEntity.ok(account)
    }


    @ApiOperation("계정 삭제")
    @DeleteMapping("/me")
    fun deleteUser(@AuthAccount account: Account): ResponseEntity<Any> {
        accountService.delete(account)
        return ResponseEntity.noContent().build()
    }

}