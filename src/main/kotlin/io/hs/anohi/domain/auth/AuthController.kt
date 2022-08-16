package io.hs.anohi.domain.auth

import io.hs.anohi.domain.auth.payload.LoginForm
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/v1/auth")
class AuthController(private val authService: AuthService) {

    @PostMapping
    fun login(@Valid @RequestBody loginForm: LoginForm): ResponseEntity<HashMap<String, String>> {
        val token = authService.login(loginForm)

        val result = HashMap<String, String>();
        result["token"] = token;
        return ResponseEntity.ok(result)
    }

}