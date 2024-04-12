package com.fhdufhdu.nocssboard.domain.board.service.dto

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class BoardServiceException {
    class NotFoundPost: ResponseStatusException(HttpStatus.NOT_FOUND, "해당 게시글을 찾을 수 없습니다.")
    class NotFoundComment: ResponseStatusException(HttpStatus.NOT_FOUND, "해당 게시글에 해당하는 댓글을 찾을 수 없습니다.")
    class NotWriterOfPost: ResponseStatusException(HttpStatus.BAD_REQUEST, "해당 게시글의 작성자가 아닙니다.")
    class NotWriterOfComment: ResponseStatusException(HttpStatus.BAD_REQUEST, "해당 댓글의 작성자가 아닙니다.")
}