package com.fhdufhdu.windows7board.domain.board.service

import com.fhdufhdu.windows7board.Windows7BoardApplicationTests
import com.fhdufhdu.windows7board.common.dto.SortDirection
import com.fhdufhdu.windows7board.domain.board.service.dto.command.PostSummariesCommand
import com.fhdufhdu.windows7board.repository.comment.CommentRepository
import com.fhdufhdu.windows7board.repository.post.PostRepository
import com.fhdufhdu.windows7board.repository.post.dto.condition.PostSummariesCondition
import com.fhdufhdu.windows7board.repository.post.dto.projection.PostSummariesProjection
import com.fhdufhdu.windows7board.repository.user.UserRepository
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import java.sql.Timestamp

@ExtendWith(MockitoExtension::class)
class BoardServiceTest {
    private val postRepository = Mockito.mock(PostRepository::class.java)
    private val commentRepository = Mockito.mock(CommentRepository::class.java)
    private val userRepository = Mockito.mock(UserRepository::class.java)
    private val boardService = BoardService(postRepository, commentRepository, userRepository)

    private fun <T> any(type: Class<T>): T = Mockito.any<T>(type)
    @Test
    fun 조회_잘_되는지() {
        //given
        val input = PostSummariesCommand(
            PostSummariesCommand.Page(0, 10),
            PostSummariesCommand.Sort(PostSummariesCommand.SortCriteria.TITLE, SortDirection.DESC),
        )
        Mockito.`when`(
            postRepository.findPostSummaries(
                any(PostSummariesCondition::class.java),
                any(Pageable::class.java)
            )
        ).thenReturn(
            PageImpl(
                arrayOf(
                    PostSummariesProjection(
                        1,
                        "userId",
                        "title",
                        "content",
                        Timestamp(System.currentTimeMillis()),
                        Timestamp(System.currentTimeMillis())
                    )
                ).toList(),
                PageRequest.of(input.page.number, input.page.size),
                1
            )
        )
        // when
        val result = boardService.filterPostSummaries(input)

        // then
        assert(result.number == 0)
        assert(result.totalPages == 1)
        assert(result.totalElements == 1.toLong())
        assert(result.posts.size == 1)
        assert(result.posts.get(0).id == 1.toLong())
    }
}