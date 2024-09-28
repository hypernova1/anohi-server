package io.hs.anohi.post.ui

import io.hs.anohi.core.Page
import io.hs.anohi.infra.config.annotations.QueryStringArgumentResolver
import io.hs.anohi.infra.security.AuthUser
import io.hs.anohi.post.application.PostService
import io.hs.anohi.post.application.payload.PostDetail
import io.hs.anohi.post.application.payload.PostPagination
import io.hs.anohi.post.application.payload.PostRequestForm
import io.hs.anohi.post.application.payload.PostUpdateForm
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

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
        authUser: AuthUser
    ): ResponseEntity<PostDetail> {
        val post = postService.create(postRequestForm, authUser.id)

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
        authUser: AuthUser
    ): ResponseEntity<Any> {
        postService.update(id, postUpdateForm, authUser.id)
        return ResponseEntity.noContent().build()
    }

    @ApiOperation("글 목록 조회 (피드)")
    @GetMapping
    fun findAll(
        @QueryStringArgumentResolver pagination: PostPagination,
        authUser: AuthUser,
    ): ResponseEntity<Page<PostDetail>> {
        val result = postService.findAll(authUser.id, pagination)
        return ResponseEntity.ok(result)
    }

    @ApiOperation("글 상세 조회")
    @GetMapping("/{id}")
    fun findOne(@PathVariable id: Long, authUser: AuthUser): ResponseEntity<PostDetail> {
        val post = postService.findById(id, authUser.id)
        return ResponseEntity.ok(post)
    }

    @ApiOperation("글 좋아요")
    @PatchMapping("/{id}/like")
    fun registerFavorite(@PathVariable id: Long, authUser: AuthUser) {
        postService.registerFavorite(id, authUser.id)
    }

    @ApiOperation("글 삭제")
    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Any> {
        postService.delete(id)
        return ResponseEntity.noContent().build()
    }

}