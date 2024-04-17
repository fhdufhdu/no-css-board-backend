package com.fhdufhdu.nocssboard.domain.user.service

import com.fhdufhdu.nocssboard.domain.user.service.dto.result.SessionUserDetail
import com.fhdufhdu.nocssboard.domain.user.service.dto.result.UserDetail
import com.fhdufhdu.nocssboard.entity.User
import com.fhdufhdu.nocssboard.repository.user.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class UserService(
    private val passwordEncoder: PasswordEncoder,
    private val userRepository: UserRepository
){

    fun fetchSessionUserDetail(id: String): SessionUserDetail{
        val user = userRepository.findByIdOrNull(id)?:throw UserServiceException.NotMatchSessionToUser()
        return SessionUserDetail(user.id)
    }

    fun registerUser(id: String, rawPassword: String) {
        val encodedPassword = passwordEncoder.encode(rawPassword)
        val existUser = userRepository.existsById(id)
        if (existUser) {
            throw ResponseStatusException(HttpStatus.CONFLICT, "이미 존재하는 아이디입니다.")
        }
        val newUser = User(id, encodedPassword)
        userRepository.save(newUser)
    }

    fun fetchUserDetail(id: String): UserDetail {
        val user = userRepository.findByIdOrNull(id)?:throw UserServiceException.NotFoundUser()
        return UserDetail(user.id)
    }
}