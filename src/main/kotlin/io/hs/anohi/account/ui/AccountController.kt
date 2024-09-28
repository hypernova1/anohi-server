package io.hs.anohi.account.ui

import io.hs.anohi.account.application.AccountService
import io.hs.anohi.account.application.payload.AccountDetail
import io.hs.anohi.account.application.payload.AccountJoinForm
import io.hs.anohi.account.application.payload.AccountUpdateForm
import io.hs.anohi.core.Page
import io.hs.anohi.infra.config.annotations.QueryStringArgumentResolver
import io.hs.anohi.infra.security.AuthUser
import io.hs.anohi.post.application.EmotionService
import io.hs.anohi.post.application.PostService
import io.hs.anohi.post.application.payload.EmotionStatistics
import io.hs.anohi.post.application.payload.PostDetail
import io.hs.anohi.post.application.payload.PostPagination
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder


@Api(tags = ["계정"])
@RestController
@RequestMapping("/v1/users")
class AccountController(
    private val accountService: AccountService,
    private val postService: PostService,
    private val emotionService: EmotionService
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

    @ApiOperation("유저 본인 정보 조회")
    @GetMapping("/me")
    fun getUserMe(authUser: AuthUser): ResponseEntity<AccountDetail> {
        val profile = accountService.findById(authUser.id)
        return ResponseEntity.ok(profile)
    }

    @ApiOperation("유저의 감정 정보 조회")
    @GetMapping("/me/emotions")
    fun getEmotionStatistics(authUser: AuthUser): ResponseEntity<List<EmotionStatistics>> {
        val profile = emotionService.getEmotionsStatistics(authUser.id)
        return ResponseEntity.ok(profile)
    }

    @ApiOperation("본인이 작성한 게시글 목록 조회")
    @GetMapping("/me/posts")
    fun getUserPosts(
        authUser: AuthUser,
        @QueryStringArgumentResolver pagination: PostPagination
    ): ResponseEntity<Page<PostDetail>> {
        val result = postService.findByUserId(authUser.id, pagination)
        return ResponseEntity.ok(result)
    }

    @ApiOperation("유저 상세 정보 조회")
    @GetMapping("/{id}")
    fun getUserDetail(@PathVariable id: Long): ResponseEntity<AccountDetail> {
        val account = accountService.findById(id, true)
        return ResponseEntity.ok(account)
    }

    @ApiOperation("유저 정보 수정")
    @PutMapping("/me")
    fun update(authUser: AuthUser, @RequestBody updateForm: AccountUpdateForm): ResponseEntity<Any> {
        this.accountService.update(authUser.id, updateForm)
        return ResponseEntity.noContent().build()
    }


    @ApiOperation("계정 삭제")
    @DeleteMapping("/me")
    fun deleteUser(authUser: AuthUser): ResponseEntity<Any> {
        accountService.delete(authUser.id)
        return ResponseEntity.noContent().build()
    }

}