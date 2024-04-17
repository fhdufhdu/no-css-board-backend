package com.fhdufhdu.nocssboard.auth

import com.fhdufhdu.nocssboard.entity.User
import com.fhdufhdu.nocssboard.repository.user.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder

class LoginService(
    private val passwordEncoder: PasswordEncoder,
    private val userRepository: UserRepository
) {
    fun fetchUser(id: String, password: String): User? {
        val user = userRepository.findByIdOrNull(id) ?: return null
        val isMatch = passwordEncoder.matches(password, user.password)
        if (!isMatch) return null

        return user
    }
}