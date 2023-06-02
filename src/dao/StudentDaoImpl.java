package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.Student;

public class StudentDaoImpl implements StudentDao {
    private final String TABLE_NAME = "students";

	public StudentDaoImpl() {
	}

	@Override
	public void setup() throws SQLException {
		try (Connection connection = Database.getConnection();
				Statement stmt = connection.createStatement();) {
			String sql = "CREATE TABLE IF NOT EXISTS "
                          + TABLE_NAME 
                          + " (studentNumber INT PRIMARY KEY NOT NULL, "
                          + "firstname VARCHAR(100) NOT NULL, "
                          + "lastname VARCHAR(100) NOT NULL, "
                          + "username VARCHAR(10) NOT NULL, FOREIGN KEY (username) REFERENCES users(username));";
			stmt.executeUpdate(sql);
		} 
	}

	@Override
	public Student getStudent(String username) throws SQLException {
		String sql = "SELECT * FROM " + TABLE_NAME + " WHERE username = ?";
		try (Connection connection = Database.getConnection(); 
				PreparedStatement stmt = connection.prepareStatement(sql);) {
			stmt.setString(1, username);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					Student student = new Student(rs.getInt("studentNumber"), rs.getString("firstname"), rs.getString("lastname"));
					return student;
				}
				return null;
			} 
		}
	}

	@Override
	public Student createStudent(String firstname, String lastname, String username) throws SQLException {
		String sql = "INSERT INTO " + TABLE_NAME + " VALUES (?, ?, ?, ?)";
		try (Connection connection = Database.getConnection();
			PreparedStatement stmt = connection.prepareStatement(sql);) {
            stmt.setInt(1, getNumberOfStudents() + 1);
			stmt.setString(2, firstname);
			stmt.setString(3, lastname);
            stmt.setString(4, username);
            System.out.println(stmt);
			stmt.executeUpdate();
			return new Student(getNumberOfStudents() + 1, firstname, lastname);
		} 
	}

    @Override 
    public int getNumberOfStudents() throws SQLException{
        String sql = "SELECT COUNT(*) FROM " + TABLE_NAME + " ;";
		try (Connection connection = Database.getConnection();
			PreparedStatement stmt = connection.prepareStatement(sql);) {
			return stmt.executeQuery().getInt("count(*)");
		}    
    }

}
