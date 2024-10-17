package io.hs.anohi.post.application

import io.hs.anohi.post.application.payload.PostRequestForm
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestPropertySource

@ActiveProfiles("test")
@TestPropertySource("classpath:application-test.yml")
@SpringBootTest
class PostServiceTest(
    @Autowired
    private val postService: PostService) {

    @Test
    fun create() {
        val form = PostRequestForm(title = "제목", content = "내용", tags = listOf("슬픔"), images = listOf())
        val postDetail = this.postService.create(form, 1L)
        assertThat(postDetail.id).isNotNull()
    }

}