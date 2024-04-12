package com.fhdufhdu.nocssboard.auth

import com.fhdufhdu.nocssboard.domain.user.controller.dto.UserRequestDto
import com.fhdufhdu.nocssboard.entity.User
import com.fhdufhdu.nocssboard.repository.user.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder

class LoginService(
    private val passwordEncoder: PasswordEncoder,
    private val userRepository: UserRepository
) {
    fun findUser(logIn: UserRequestDto.LogIn): User? {
        val user = userRepository.findByIdOrNull(logIn.id) ?: return null
        val isMatch = passwordEncoder.matches(logIn.password, user.password)
        if (!isMatch) return null

        return user
    }
}