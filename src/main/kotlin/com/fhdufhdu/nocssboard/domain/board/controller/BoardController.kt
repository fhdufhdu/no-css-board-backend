package com.fhdufhdu.nocssboard.domain.board.controller

import com.fhdufhdu.nocssboard.common.dto.SortDirection
import com.fhdufhdu.nocssboard.domain.board.controller.dto.BoardRequestDto
import com.fhdufhdu.nocssboard.domain.board.service.BoardService
import com.fhdufhdu.nocssboard.domain.board.service.dto.FindPostsDto
import com.fhdufhdu.nocssboard.domain.board.service.dto.FindPostsEnum
import jakarta.validation.Valid
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@PreAuthorize("isAuthenticated()")
@RequestMapping("board")
@Validated
class BoardController(
    private val boardService: BoardService
) {
    @PreAuthorize("permitAll")
    @PostMapping("posts")
    @ResponseBody
    fun findPosts(@Valid @RequestBody getPosts: BoardRequestDto.GetPosts): FindPostsDto.Return {

        val page = FindPostsDto.Input.Page(getPosts.pageNumber, getPosts.pageSize)
        val sort = FindPostsDto.Input.Sort(getPosts.sortCriteria, getPosts.sortDirection)
        var search: FindPostsDto.Input.Search? = null
        if (getPosts.searchQuery != null && getPosts.searchCriteria != null)
            search = FindPostsDto.Input.Search(getPosts.searchQuery!!, getPosts.searchCriteria!!)

        val findPostsInput = FindPostsDto.Input(page, sort, search)

        return boardService.findPosts(findPostsInput)
    }
}