package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;

import model.Course;
import model.Days;
import util.CsvCourseReader;

public class CourseDaoImpl implements CourseDao {
    private final String TABLE_NAME = "courses";

	public CourseDaoImpl() {
	}

	@Override
	public void setup() throws SQLException {
		try (Connection connection = Database.getConnection();
				Statement stmt = connection.createStatement();) {
			String sql = "CREATE TABLE IF NOT EXISTS "
                + TABLE_NAME 
                + " (courseName varchar(20) NOT NULL PRIMARY KEY,"
                + " capacity int NOT NULL,"
                + " year varchar(4) NOT NULL,"
                + " delivery varchar(7) NOT NULL,"
                + " day varchar(7) NOT NULL,"
                + " time varchar(6) NOT NULL,"
                + " duration double NOT NULL);"; 
			stmt.executeUpdate(sql);
		} 
	}

	@Override
	public HashSet<Course> fetchAllCourses() throws SQLException {
        HashSet<Course> allCourses = new HashSet<>();
		String sql = "SELECT * FROM " + TABLE_NAME + " ;";
		try (Connection connection = Database.getConnection(); 
				PreparedStatement stmt = connection.prepareStatement(sql);) {
			try (ResultSet rs = stmt.executeQuery()) {
                    for(int i = 0; i < getNumberOfCourses(); i++){
        				if (rs.next()) {
                            Course course = 
                                new Course(rs.getString("courseName"), rs.getInt("capacity"), rs.getString("year"), rs.getString("delivery"), Days.valueOf(rs.getString("day").toUpperCase()), rs.getString("time"), rs.getDouble("duration"));
                            allCourses.add(course);
                        }
                    }
			} 
            return allCourses;
		}
	}
    

    @Override
    public void populate() throws SQLException {
        if(getNumberOfCourses() == 0){
            String sql = "INSERT INTO " + TABLE_NAME + " VALUES(?,?,?,?,?,?,?)";
            HashSet<Course> allCourses = CsvCourseReader.returnAllCourses();
            try (Connection connection = Database.getConnection(); 
                PreparedStatement stmt = connection.prepareStatement(sql);) {

                for(Course course: allCourses){
                    stmt.setString(1, course.getCourseName());
                    stmt.setInt(2, course.getCapacity());
                    stmt.setString(3, course.getYear());
                    stmt.setString(4, course.getDeliveryMode());
                    stmt.setString(5, course.getDayOfLecture());
                    stmt.setString(6, course.getTime());
                    stmt.setDouble(7, course.getDuration());

                    stmt.addBatch();
                }
                stmt.executeBatch();
            } 
        }
    }

    @Override 
    public int getNumberOfCourses() throws SQLException{
        String sql = "SELECT COUNT(*) FROM " + TABLE_NAME + " ;";
		try (Connection connection = Database.getConnection();
			PreparedStatement stmt = connection.prepareStatement(sql);) {
			return stmt.executeQuery().getInt("count(*)");
		}    
    }
   
 }


