package com.web.curation;

import java.sql.Connection;
import java.sql.DriverManager;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class WebCurationApplicationTests {

	private final String DRIVER = "com.mysql.cj.jdbc.Driver";
	private final String URL = "jdbc:mariadb://127.0.0.1/test";
	private final String USER = "ssafy";
	private final String PW = "ssafy";

	@Test
	public void test() {

		try {
			Class.forName(DRIVER);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try (Connection con = DriverManager.getConnection(URL, USER, PW)) {
			System.out.println(con);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}


}
