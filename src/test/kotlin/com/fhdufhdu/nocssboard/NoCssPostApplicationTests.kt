package com.fhdufhdu.nocssboard

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fhdufhdu.nocssboard.domain.board.controller.dto.BoardRequestDto
import jakarta.validation.Validator
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.io.File

@SpringBootTest
class NoCssPostApplicationTests(
	@Autowired
	private val validator: Validator
) {

	@Test
	fun contextLoads() {
	}

}
