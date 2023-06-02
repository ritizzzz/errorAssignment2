package dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;

import model.Course;
import model.Student;

public interface EnrollmentDao {
    void setup() throws SQLException;
    void enrollStudent(Student student, Course course) throws SQLException;
    void withdrawStudent(Student student, Course course) throws SQLException;
    HashSet<Course> getEnrollments(Student student) throws SQLException;
    HashMap<Course, Integer> getCountEnrollments() throws SQLException;
}
