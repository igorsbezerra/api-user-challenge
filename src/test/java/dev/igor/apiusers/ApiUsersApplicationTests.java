package dev.igor.apiusers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApiUsersApplicationTests {
	@Autowired ApiUsersApplication app;
	@Test
	void contextLoads() {
	}

	@Test
	public void applicationContextTest() {
    	Assertions.assertDoesNotThrow(() -> app.main(new String[] {}));
	}

}
