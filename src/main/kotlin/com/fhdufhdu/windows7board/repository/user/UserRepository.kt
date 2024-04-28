package com.fhdufhdu.windows7board.repository.user

import com.fhdufhdu.windows7board.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<User, String> {
}