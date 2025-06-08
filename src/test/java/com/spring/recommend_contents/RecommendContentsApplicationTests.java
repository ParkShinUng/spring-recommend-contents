package com.spring.recommend_contents;

import com.spring.recommend_contents.user.UserService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RecommendContentsApplicationTests {

	@Autowired
	private UserService userService;

	@Test
	void contextLoads() {
	}

	@Transactional
	@Test
	void testCreateUser() {
		this.userService.create("tlsdnd001@gmail.com", "1234");
	}
}
