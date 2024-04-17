package com.fhdufhdu.nocssboard.domain.user.service

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class UserServiceException {
    class NotMatchSessionToUser: ResponseStatusException(HttpStatus.CONFLICT, "로그인 세션과 유저 정보가 일치하지 않습니다.")
    class NotFoundUser: ResponseStatusException(HttpStatus.NOT_FOUND, "유저 정보가 없습니다.")
}