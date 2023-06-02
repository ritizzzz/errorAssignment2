package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import model.Course;
import model.Student;

public class EnrollmentDaoImpl implements EnrollmentDao {
    private final String TABLE_NAME = "enrollments";


    @Override
    public void setup() throws SQLException {
        try (Connection connection = Database.getConnection();
				Statement stmt = connection.createStatement();) {
			String sql = "CREATE TABLE IF NOT EXISTS " 
                + TABLE_NAME 
                + " (studentNumber INT NOT NULL," 
                + " courseName VARCHAR(20) NOT NULL,"
                + " FOREIGN KEY (studentNumber) REFERENCES students(studentNumber),"
                + " FOREIGN KEY (courseName) REFERENCES courses(courseName)"
                + " PRIMARY KEY(studentNumber, courseName));";
              
			stmt.executeUpdate(sql);
		} 
    }

    @Override
    public void enrollStudent(Student student, Course course) throws SQLException {
        String sql = "INSERT INTO " + TABLE_NAME + " VALUES (?, ?)";
		try (Connection connection = Database.getConnection();
				PreparedStatement stmt = connection.prepareStatement(sql);) {
			stmt.setInt(1, student.getStudentNumber());
			stmt.setString(2, course.getCourseName());

			stmt.executeUpdate();
		}        

    }

    @Override
    public void withdrawStudent(Student student, Course course) throws SQLException {
        String sql = "DELETE FROM " + TABLE_NAME + "WHERE studentNumber = ? AND courseName = ?";
		try (Connection connection = Database.getConnection();
				PreparedStatement stmt = connection.prepareStatement(sql);) {
			stmt.setInt(1, student.getStudentNumber());
			stmt.setString(2, course.getCourseName());

			stmt.executeUpdate();
		}        
    }

    @Override
    public HashSet<Course> getEnrollments(Student student) throws SQLException {
        HashSet<Course> enrolledCourses = new HashSet<>();
        String sql = "SELECT courses.* FROM "
                    +  TABLE_NAME
                    + " JOIN courses ON enrollments.courseName = courses.courseName"
                    + " WHERE studentNumber = " 
                    + student.getStudentNumber() 
                    + ";";
        ;
		try (Connection connection = Database.getConnection();
				PreparedStatement stmt = connection.prepareStatement(sql);) {
			stmt.setInt(1, student.getStudentNumber());
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Course course = 
                     new Course(rs.getString("courseName"), rs.getInt("capacity"), rs.getString("year"), rs.getString("delivery"), Days.valueOf(rs.getString("day").toUpperCase()), rs.getString("time"), rs.getDouble("duration"));
                enrolledCourses.add(course);
            }
		}

        return enrolledCourses;
    }

    @Override
    public HashMap<Course, Integer> getCountEnrollments() throws SQLException {
        HashMap<Course, Integer> count = new HashMap<>();
        String sql = "SELECT courses.*, COUNT(studentNumber) AS enrollmentCount FROM "
                    +  TABLE_NAME
                    + " JOIN courses ON enrollments.courseName = courses.courseName"
                    + " GROUP BY enrollments.courseName;"; 
                
		try (Connection connection = Database.getConnection();
				PreparedStatement stmt = connection.prepareStatement(sql);) {
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Course course = 
                     new Course(rs.getString("courseName"), rs.getInt("capacity"), rs.getString("year"), rs.getString("delivery"), Days.valueOf(rs.getString("day").toUpperCase()), rs.getString("time"), rs.getDouble("duration"));
                Integer numberOfEnrollments = rs.getInt("enrollmentCount");
                count.put(course, numberOfEnrollments);
            }
		}

        return count;
    }
    
}
