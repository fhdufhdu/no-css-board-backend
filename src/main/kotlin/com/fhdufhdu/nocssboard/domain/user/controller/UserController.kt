package com.fhdufhdu.nocssboard.domain.user.controller

import com.fhdufhdu.nocssboard.domain.user.controller.dto.UserRequestDto
import com.fhdufhdu.nocssboard.domain.user.service.dto.UserServiceDto
import com.fhdufhdu.nocssboard.domain.user.service.UserService
import com.fhdufhdu.nocssboard.domain.user.service.dto.ExistUserId
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("user")
class UserController(
    private val userService: UserService
) {
    @PostMapping("sign-up")
    fun signUp(@Valid @RequestBody signUp: UserRequestDto.SignUp) {
        val id = signUp.id
        val rawPassword = signUp.password
        userService.addUser(id, rawPassword!!)
    }

    @PostMapping("log-in")
    fun logIn(@RequestBody logIn: UserRequestDto.LogIn) {
    }

    @GetMapping("{id}/existence")
    fun existUserId(@PathVariable("id") id: String): ExistUserId.Return  {

        return userService.existUserId(id)
    }
}