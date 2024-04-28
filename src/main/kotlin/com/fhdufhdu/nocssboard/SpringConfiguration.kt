package com.fhdufhdu.nocssboard

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fhdufhdu.nocssboard.auth.LoginFilter
import com.fhdufhdu.nocssboard.auth.LoginService
import com.fhdufhdu.nocssboard.repository.user.UserRepository
import com.querydsl.jpa.impl.JPAQueryFactory
import io.swagger.v3.core.jackson.ModelResolver
import jakarta.persistence.EntityManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@ComponentScan
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true)
class SpringConfiguration(
        private val userRepository: UserRepository,
        private val entityManager: EntityManager
) {
    @Bean
    @Throws(Exception::class)
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .formLogin {
                it.disable()
            }
            .httpBasic {
                it.disable()
            }
            .csrf {
                it.disable()
            }
            .cors{}
            .addFilterBefore(
                loginFilter()
                    .setLoginPath("/user/login")
                    .setMaxInactiveInterval(60 * 60 * 24 * 3),
                UsernamePasswordAuthenticationFilter::class.java
            )
//            .authorizeHttpRequests {
//                it.requestMatchers("/user/log-in", "/error").permitAll()
//                    .anyRequest().authenticated()
//            }

        return http.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    fun loginService(): LoginService {
        return LoginService(passwordEncoder(), userRepository)
    }

    @Bean
    fun loginFilter(): LoginFilter {
        return LoginFilter(loginService())
    }

    @Bean
    fun queryFactory(): JPAQueryFactory {
        return JPAQueryFactory(entityManager)
    }

    @Bean
    fun modelResolver(objectMapper: ObjectMapper): ModelResolver {
        return ModelResolver(objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE))
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()
        configuration.allowedOrigins = listOf("http://localhost:3000", "http://127.0.0.1:3000", "http://localhost", "http://fhdufhdu.hopto.org")
        configuration.allowedMethods = listOf("POST", "GET", "DELETE", "PUT")
        configuration.allowedHeaders = listOf("*")
        configuration.allowCredentials = true

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }

}