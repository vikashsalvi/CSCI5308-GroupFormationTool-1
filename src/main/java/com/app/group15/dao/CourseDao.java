package com.app.group15.dao;


import com.app.group15.injectors.CourseInstructorMapperInjectorInterface;
import com.app.group15.injectors.CourseStudentMapperDaoInjectorInterface;
import com.app.group15.model.Course;
import com.app.group15.model.Persistence;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

@SuppressWarnings("rawtypes")
public class CourseDao
		implements Dao, CourseInstructorMapperInjectorInterface, CourseStudentMapperDaoInjectorInterface {

	private Connection connection;
	private CourseInstructorMapperDao courseInstructorMapperDao;
	private CourseStudentMapperDao courseStudentMapperDao;

	private final static Logger LOGGER = Logger.getLogger(CourseDao.class.getName());

	@Override
	public void injectConnection(Connection connection) {
		this.connection = connection;
	}

	@Override
	public Course get(int id) {
		String query = "SELECT * FROM table_course WHERE id=?";
		Course course = new Course();
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, id);
			try (ResultSet result = statement.executeQuery()) {
				while (result.next()) {

					course.setId(result.getInt("id"));
					course.setName(result.getString("name"));

				}
			}
		} catch (SQLException e) {

			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		}
		return course;
	}

	@Override
	public ArrayList<Course> getAll() {
		String query = "SELECT * FROM table_course";
		ArrayList<Course> coursesList = new ArrayList<Course>();
		try (PreparedStatement statement = connection.prepareStatement(query);
				ResultSet result = statement.executeQuery()) {
			while (result.next()) {
				Course course = new Course();
				course.setId(result.getInt("id"));
				course.setName(result.getString("name"));
				coursesList.add(course);
			}

		} catch (SQLException e) {

			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		}

		return coursesList;
	}

	@Override
	public int save(Persistence course) {
		Course courseEntity = (Course) course;
		String query = "INSERT INTO table_course(name) VALUES(?)";
		int courseId = 0;
		try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
			connection.setAutoCommit(false);
			statement.setString(1, courseEntity.getName());
			statement.executeUpdate();
			connection.commit();
			try (ResultSet result = statement.getGeneratedKeys()) {

				if (result.first()) {

					courseId = result.getInt(1);
				}
			}
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				LOGGER.log(Level.SEVERE, e.getMessage(), e);
			}

			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		}
		return courseId;

	}

	@Override
	public void update(Persistence course, int id) {
		Course courseEntity = (Course) course;
		String query = "UPDATE table_course SET name=? WHERE id=?";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			connection.setAutoCommit(false);
			statement.setString(1, courseEntity.getName());
			statement.setInt(2, id);
			statement.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				LOGGER.log(Level.SEVERE, e.getMessage(), e);
			}
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		}

	}

	@Override
	public void delete(int id) {
		String query = "DELETE FROM table_course WHERE id=?";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			connection.setAutoCommit(false);
			courseInstructorMapperDao.deleteByCourseId(id);
			courseStudentMapperDao.deletByCourseId(id);
			statement.setInt(1, id);
			statement.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				LOGGER.log(Level.SEVERE, e.getMessage(), e);
			}

			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		}

	}

	@Override
	public void injectCourseInstructorMapperDao(CourseInstructorMapperDao courseInstructorMapperDao) {
		this.courseInstructorMapperDao = courseInstructorMapperDao;

	}

	@Override
	public void injectCourseStudentMapperDao(CourseStudentMapperDao courseStudentMapperDao) {
		this.courseStudentMapperDao = courseStudentMapperDao;

	}

}