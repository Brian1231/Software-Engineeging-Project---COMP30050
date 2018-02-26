package noc_db;

import java.sql.*;

public class DB_Manager {

	public void connect() {
		Connection conn = null;
		try {
			// db parameters
			String url = "jdbc:sqlite:noc.db";
			// create a connection to the database
			conn = DriverManager.getConnection(url);

			System.out.println("Connection to SQLite has been established.");

			String sql = "SELECT * FROM fictionalWorlds";
			Statement stmt  = conn.createStatement();
			ResultSet rs    = stmt.executeQuery(sql);


			// loop through the result set
			while (rs.next()) {
				System.out.println(
						rs.getString("id") + "\t" +
						rs.getString("type1") + "\t" +
						rs.getString("type2") + "\t" +
						rs.getString("type3") + "\t" +
						rs.getString("type4") + "\t" +
						rs.getString("type5") + "\t" +
						rs.getString("type6") + "\t"
						);
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException ex) {
				System.out.println(ex.getMessage());
			}
		}
	}
}
