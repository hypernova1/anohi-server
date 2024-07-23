package io.hs.anohi.post.ui

import io.hs.anohi.core.Page
import io.hs.anohi.domain.account.Account
import io.hs.anohi.domain.post.payload.PostDetail
import io.hs.anohi.domain.post.payload.PostPagination
import io.hs.anohi.domain.post.payload.PostRequestForm
import io.hs.anohi.domain.post.payload.PostUpdateForm
import io.hs.anohi.infra.annotations.QueryStringArgumentResolver
import io.hs.anohi.infra.security.AuthAccount
import io.hs.anohi.post.application.PostService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import javax.validation.Valid

@Api(tags = ["글"])
@RestController
@RequestMapping("/v1/posts")
class PostController(
    private val postService: PostService
) {

    @ApiOperation("글 생성")
    @PostMapping
    fun create(
        @Valid @RequestBody postRequestForm: PostRequestForm,
        @AuthAccount account: Account
    ): ResponseEntity<PostDetail> {
        val post = postService.create(postRequestForm, account)

        val location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(post.id)
            .toUri()

        return ResponseEntity.created(location).body(post)
    }

    @ApiOperation("글 수정")
    @PatchMapping("/{id}")
    fun update(
        @PathVariable id: Long,
        @RequestBody postUpdateForm: PostUpdateForm,
        @AuthAccount account: Account
    ): ResponseEntity<Any> {
        postService.update(id, postUpdateForm, account)
        return ResponseEntity.noContent().build()
    }

    @ApiOperation("글 목록 조회 (피드)")
    @GetMapping
    fun findAll(
        @QueryStringArgumentResolver pagination: PostPagination,
        @AuthAccount account: Account,
    ): ResponseEntity<Page<PostDetail>> {
        val result = postService.findAll(account, pagination)
        return ResponseEntity.ok(result)
    }

    @ApiOperation("글 상세 조회")
    @GetMapping("/{id}")
    fun findOne(@PathVariable id: Long, @AuthAccount account: Account): ResponseEntity<PostDetail> {
        val post = postService.findById(id, account)
        return ResponseEntity.ok(post)
    }

    @ApiOperation("글 좋아요")
    @PatchMapping("/{id}/like")
    fun registerFavorite(@PathVariable id: Long, @AuthAccount account: Account) {
        postService.registerFavorite(id, account)
    }

    @ApiOperation("글 삭제")
    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Any> {
        postService.delete(id)
        return ResponseEntity.noContent().build()
    }

}