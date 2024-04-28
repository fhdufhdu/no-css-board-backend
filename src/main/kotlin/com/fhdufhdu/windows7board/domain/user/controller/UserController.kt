package com.fhdufhdu.windows7board.domain.user.controller

import com.fhdufhdu.windows7board.domain.user.controller.dto.LogInRequest
import com.fhdufhdu.windows7board.domain.user.controller.dto.SignUpRequest
import com.fhdufhdu.windows7board.domain.user.service.UserService
import com.fhdufhdu.windows7board.domain.user.service.dto.result.SessionUserDetail
import com.fhdufhdu.windows7board.domain.user.service.dto.result.UserDetail
import jakarta.validation.Valid
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("user")
class UserController(
    private val userService: UserService
) {
    @GetMapping
    fun getSessionUserDetail(@AuthenticationPrincipal userId: String): SessionUserDetail {
        return userService.fetchSessionUserDetail(userId)
    }

    @PostMapping("signup")
    fun signUp(@Valid @RequestBody body: SignUpRequest) {
        val id = body.id
        val rawPassword = body.password
        userService.registerUser(id, rawPassword!!)
    }

    // swagger용 가짜 엔드포인트
    @PostMapping("login")
    fun logIn(@RequestBody body: LogInRequest) {}

    @GetMapping("my")
    fun getMyUserDetail(@AuthenticationPrincipal id: String): UserDetail {
        return userService.fetchUserDetail(id)
    }

    @GetMapping("{userId}")
    fun getUserDetail(@PathVariable("userId") userId: String): UserDetail {
        return userService.fetchUserDetail(userId)
    }
}