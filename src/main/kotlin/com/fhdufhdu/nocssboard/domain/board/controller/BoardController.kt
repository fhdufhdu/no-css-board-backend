package com.fhdufhdu.nocssboard.domain.board.controller

import com.fhdufhdu.nocssboard.domain.board.controller.dto.BoardRequestDto
import com.fhdufhdu.nocssboard.domain.board.service.BoardService
import com.fhdufhdu.nocssboard.domain.board.service.dto.BoardServiceDto
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@PreAuthorize("isAuthenticated()")
@RequestMapping("board")
@Validated
@ResponseBody
class BoardController(
        private val boardService: BoardService
) {
    @PreAuthorize("permitAll")
    @GetMapping("posts")
    fun findPosts(@Valid @ModelAttribute query: BoardRequestDto.FindPosts): BoardServiceDto.FindPosts.Return {
        val page = BoardServiceDto.FindPosts.Input.Page(query.pageNumber, query.pageSize)
        val sort = BoardServiceDto.FindPosts.Input.Sort(query.sortCriteria, query.sortDirection)
        var search: BoardServiceDto.FindPosts.Input.Search? = null
        if (query.searchQuery != null && query.searchCriteria != null)
            search = BoardServiceDto.FindPosts.Input.Search(query.searchQuery!!, query.searchCriteria!!)

        val findPostsInput = BoardServiceDto.FindPosts.Input(page, sort, search)

        return boardService.findPosts(findPostsInput)
    }

    @PreAuthorize("permitAll")
    @GetMapping("post/{postId}")
    fun findOnePost(@PathVariable postId: Long): BoardServiceDto.FindOnePost.Return {
        return boardService.findOnePost(postId)
    }

    @PostMapping("post")
    @ResponseStatus(HttpStatus.CREATED)
    fun addPost(@AuthenticationPrincipal userId: String, @Valid @RequestBody body: BoardRequestDto.AddPost) {
        boardService.addPost(body.title, body.content, userId)
    }

    @DeleteMapping("post/{postId}")
    fun deletePost(@PathVariable postId: Long, @AuthenticationPrincipal userId: String) {
        boardService.deletePost(postId, userId)
    }

    @PreAuthorize("permitAll")
    @GetMapping("post/{postId}/comments")
    fun findComments(@Valid @ModelAttribute query: BoardRequestDto.FindComments, @PathVariable postId: Long): BoardServiceDto.FindComments.Return {
        return boardService.findComments(postId, query.pageNumber, query.pageSize)
    }

    @PostMapping("post/{postId}/comment")
    fun addComment(@Valid @RequestBody body: BoardRequestDto.AddComment, @PathVariable postId: Long, @AuthenticationPrincipal userId: String) {
        boardService.addComment(body.content, postId, userId)
    }

    @DeleteMapping("post/{postId}/comment/{commentId}")
    fun deleteComment(@PathVariable postId: Long, @PathVariable commentId: Long, @AuthenticationPrincipal userId: String) {
        boardService.deleteComment(postId, commentId, userId)
    }

}