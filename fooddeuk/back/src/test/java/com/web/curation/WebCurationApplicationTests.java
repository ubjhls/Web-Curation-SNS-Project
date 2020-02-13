package com.web.curation;

import java.sql.Connection;
import java.sql.DriverManager;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class WebCurationApplicationTests {

	private final String DRIVER = "com.mysql.cj.jdbc.Driver";
<<<<<<< HEAD
	private final String URL = "jdbc:mariadb://127.0.0.1/test";
	private final String USER = "ssafy";
	private final String PW = "ssafy";
=======
	private final String URL = "jdbc:mysql://52.78.215.167/fooddeuk";
	private final String USER = "root";
	private final String PW = "fooddeuk1234!";
>>>>>>> 9cf119a9e5d843d95f5b5df0997a4d5e97091c0c

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
