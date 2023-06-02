package dao;

import java.sql.SQLException;

import model.Student;

public interface StudentDao {
	void setup() throws SQLException;
	Student getStudent(String username) throws SQLException;
	Student createStudent(String firstName, String lastName, String username) throws SQLException;
    int getNumberOfStudents() throws SQLException;
}
