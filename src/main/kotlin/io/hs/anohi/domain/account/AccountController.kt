package io.hs.anohi.domain.account

import io.hs.anohi.domain.account.payload.*
import io.hs.anohi.infra.security.AuthAccount
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
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
        val account = accountService.create(accountJoinForm)

        val location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(account.id)
            .toUri()

        return ResponseEntity.created(location).build()
    }

    @ApiOperation("이메일 존재 여부 확인")
    @PostMapping("/check-email-duplication")
    fun existsEmail(@RequestBody @Valid request: ExistsEmailRequest): ResponseEntity<ExistsEmailResponse> {
        val existsEmail = accountService.existsEmail(request.email)
        println(existsEmail)
        return ResponseEntity.ok().body(ExistsEmailResponse(existsEmail));
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