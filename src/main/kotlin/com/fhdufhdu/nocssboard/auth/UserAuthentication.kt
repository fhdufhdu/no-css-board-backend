package com.fhdufhdu.nocssboard.auth

import com.fhdufhdu.nocssboard.entity.User
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority

class UserAuthentication(val user: User): Authentication {
    override fun getName(): String {
        return user.id
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return ArrayList()
    }

    override fun getCredentials(): String {
        return user.password
    }

    // 사용자 상세 정보가 들어감
    override fun getDetails(): Any? {
        return null
    }

    // 일반적으로 ID가 들어감
    override fun getPrincipal(): String {
        return user.id
    }

    override fun isAuthenticated(): Boolean {
        return true
    }

    override fun setAuthenticated(isAuthenticated: Boolean) {
    }

}