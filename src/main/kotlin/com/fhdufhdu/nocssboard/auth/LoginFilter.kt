package com.fhdufhdu.nocssboard.auth

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fhdufhdu.nocssboard.domain.user.controller.dto.UserRequestDto
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.context.HttpSessionSecurityContextRepository
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class LoginFilter(
    private val loginService: LoginService
) : OncePerRequestFilter() {
    private var loginPath: String = ""
    private var maxInactiveInterval: Int = 60 * 60 * 24 * 3

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        println(request.session.id)
        if (request.method != HttpMethod.POST.name() || request.requestURI != loginPath) {
            filterChain.doFilter(request, response)
//        } else if (request.method != HttpMethod.POST.name()) {
//            failLogin(response, HttpStatus.METHOD_NOT_ALLOWED)
        } else {
            val newRequest = RequestWrapper(request)
            try {
                val om = jacksonObjectMapper()
                val loginInDto = om.readValue(newRequest.inputStream, UserRequestDto.LogIn::class.java)

                val user = loginService.findUser(loginInDto) ?: return failLogin(response, HttpStatus.FORBIDDEN)

                val auth = UserAuthentication(user)

                // 현재 리퀘스트 바운드에서 적용되는 부분
                SecurityContextHolder.getContext().authentication = auth
                // 리퀘스트 바운드 영역 데이터를 글로벌한 영역으로 저장함. 향후 다른 리퀘스트에서도 세션이 유지되도록
                newRequest.session.setAttribute(
                    HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                    SecurityContextHolder.getContext()
                )
                // 해당 세션 비활성화 유지 시간 설정
                newRequest.session.maxInactiveInterval = maxInactiveInterval
            } catch (err: Exception) {
                err.printStackTrace()
                return failLogin(response, HttpStatus.FORBIDDEN)
            }
        }
    }

    private fun failLogin(response: HttpServletResponse, status: HttpStatus) {
        response.sendError(status.value())
    }

    fun setLoginPath(loginPath: String): LoginFilter {
        this.loginPath = loginPath
        return this
    }

    fun setMaxInactiveInterval(value: Int): LoginFilter{
        this.maxInactiveInterval = value
        return this
    }
}