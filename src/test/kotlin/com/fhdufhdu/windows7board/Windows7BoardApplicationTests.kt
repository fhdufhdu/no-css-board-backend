package com.fhdufhdu.windows7board

import jakarta.validation.Validator
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class Windows7BoardApplicationTests(
	@Autowired
	private val validator: Validator
) {

	@Test
	fun contextLoads() {
	}

}