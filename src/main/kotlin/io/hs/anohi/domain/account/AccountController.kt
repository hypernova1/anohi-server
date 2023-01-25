package io.hs.anohi.domain.account

import io.hs.anohi.domain.account.payload.AccountDetail
import io.hs.anohi.domain.account.payload.AccountJoinForm
import io.hs.anohi.domain.account.payload.AccountSummary
import io.hs.anohi.domain.account.payload.AccountUpdateForm
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
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


@Api(tags = ["계정"])
@RestController
@RequestMapping("/v1/accounts")
class AccountController(
    @Autowired
    private val accountService: AccountService
) {
    @ApiOperation("계정 생성")
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

    @ApiOperation("이메일 존재 여부 확인")
    @GetMapping("/email/{email}/existence")
    fun existsEmail(@PathVariable email: String): ResponseEntity<Any> {
        accountService.existsEmail(email)
        return ResponseEntity.ok().build()
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

    @ApiOperation("유저 상세 정보 조회")
    @GetMapping("/{id}")
    fun getUserDetail(@PathVariable id: Long): ResponseEntity<AccountDetail> {
        val account = accountService.findById(id)

        return ResponseEntity.ok(account)
    }

    @ApiOperation("계정 정보 수정")
    @PutMapping("/{id}")
    fun modifyUserInfo(
        @PathVariable id: Long,
        @Valid @RequestBody request: AccountUpdateForm
    ): ResponseEntity<Any> {

        accountService.update(id, request)
        val account = accountService.findById(id)

        return ResponseEntity.ok().build()
    }


    @ApiOperation("계정 삭제")
    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: Long): ResponseEntity<Any> {
        accountService.delete(id)

        return ResponseEntity.ok().build()
    }

}