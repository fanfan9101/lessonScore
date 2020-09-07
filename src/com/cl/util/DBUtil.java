package com.cl.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBUtil {

	public static Connection getConnection(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/lesson", "root", "x5");
			return conn;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void closeDB(PreparedStatement preparedStatement,Connection connection) throws SQLException {
		preparedStatement.close();
		connection.close();
	}
	
	public static void main(String[] args) {
		Connection conn = getConnection();
	}
	
}
