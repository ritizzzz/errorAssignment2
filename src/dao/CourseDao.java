package dao;

import java.sql.SQLException;
import java.util.HashSet;

import model.Course;

public interface CourseDao {
	void setup() throws SQLException;
	HashSet<Course> fetchAllCourses() throws SQLException;
    void populate() throws SQLException;
    int getNumberOfCourses() throws SQLException;
}
