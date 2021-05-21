package org.training.kafka;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLDatabaseConnection {

	public static Connection dataBaseConnection() throws SQLException {

		Connection con = null;
	

		String username = "root";

		DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());

		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/kafka?user=" + username + "&password=password");
		
		con.setAutoCommit(false);

		return con;
	}
}
