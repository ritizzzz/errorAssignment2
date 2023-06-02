package model;

import java.sql.SQLException;

import dao.CourseDao;
import dao.CourseDaoImpl;
import dao.EnrollmentDao;
import dao.EnrollmentDaoImpl;
import dao.StudentDao;
import dao.StudentDaoImpl;
import dao.UserDao;
import dao.UserDaoImpl;

public class Model {
	private UserDao userDao;
	private User currentUser; 
	private StudentDao studentDao;
	private Student currentStudent; 
	private CourseDao courseDao;
	private EnrollmentDao enrollmentDao;
	
	public Model() {
		userDao = new UserDaoImpl();
		studentDao = new StudentDaoImpl();
		courseDao = new CourseDaoImpl();
		enrollmentDao = new EnrollmentDaoImpl();
	}
	
	public void setup() throws SQLException {
		userDao.setup();
		studentDao.setup();
		courseDao.setup();
		courseDao.populate();
		enrollmentDao.setup();
	}

	public UserDao getUserDao() {
		return userDao;
	}
	
	public User getCurrentUser() {
		return this.currentUser;
	}
	
	public void setCurrentUser(User user) {
		currentUser = user;
	}

	public StudentDao getStudentDao() {
		return studentDao;
	}
	
	public Student getCurrentStudent() {
		return this.currentStudent;
	}
	
	public void setCurrentStudent(Student student) {
		currentStudent = student;
	}

	public CourseDao getCourseDao(){
		return courseDao;
	}

	public EnrollmentDao getEnrollmentDao(){
		return enrollmentDao;
	}
}
