package com.fhdufhdu.nocssboard.domain.user.service.dto

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class UserServiceException {
    class NotMatchSessionToUser: ResponseStatusException(HttpStatus.CONFLICT, "로그인 세션과 유저 정보가 일치하지 않습니다.")
}