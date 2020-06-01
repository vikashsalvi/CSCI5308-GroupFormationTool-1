package com.app.group15.config;


import com.app.group15.dao.CourseInstructorMapperDao;
import com.app.group15.dao.CourseStudentMapperDao;
import com.app.group15.dao.UserDao;
import com.app.group15.injectors.CourseInstructorMapperDaoInjectorService;
import com.app.group15.injectors.CourseStudentMapperDaoInjectorService;
import com.app.group15.injectors.UserRoleDaoInjectorService;
import com.app.group15.notifications.EmailNotifierImpl;
import com.app.group15.utility.GroupFormationToolLogger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;

public class AppConfig {

    private static AppConfig singletonAppConfig = null;

    private EmailNotifierImpl emailNotifier;
    private GroupFormationToolLogger groupFormationToolLogger;
    private Properties properties;
    private CourseStudentMapperDaoInjectorService courseStudentMapperDaoInjectorService;
    private CourseInstructorMapperDaoInjectorService courseInstructorMapperDaoInjectorService;
    private UserRoleDaoInjectorService userRoleDaoInjectorService;
    private CourseStudentMapperDao courseStudentMapperDao;
    private CourseInstructorMapperDao courseInstructorMapperDao;
    private UserDao userDao;


    private AppConfig() {

        properties = new Properties();
        String propertyFilePath = "src/main/resources/application.properties";
        try (FileInputStream in = new FileInputStream(propertyFilePath)) {
            properties.load(in);
        } catch (IOException ex) {
            GroupFormationToolLogger.log(Level.SEVERE, ex.getMessage(), ex);
        }

        emailNotifier = new EmailNotifierImpl();
        groupFormationToolLogger = new GroupFormationToolLogger();

        courseInstructorMapperDaoInjectorService = new CourseInstructorMapperDaoInjectorService();
        courseStudentMapperDaoInjectorService = new CourseStudentMapperDaoInjectorService();
        userRoleDaoInjectorService = new UserRoleDaoInjectorService();
        courseStudentMapperDao = new CourseStudentMapperDao();
        courseInstructorMapperDao = new CourseInstructorMapperDao();
        userDao = new UserDao();


    }

    public static AppConfig getSingletonAppConfig() {
        return singletonAppConfig;
    }

    public static void setSingletonAppConfig(AppConfig singletonAppConfig) {
        AppConfig.singletonAppConfig = singletonAppConfig;
    }

    public static AppConfig getInstance() {
        if (null == AppConfig.getUniqueInstance()) {
            singletonAppConfig = new AppConfig();
        }
        return AppConfig.singletonAppConfig;
    }

    private static AppConfig getUniqueInstance() {
        return singletonAppConfig;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public CourseInstructorMapperDao getCourseInstructorMapperDao() {
        return courseInstructorMapperDao;
    }

    public void setCourseInstructorMapperDao(CourseInstructorMapperDao courseInstructorMapperDao) {
        this.courseInstructorMapperDao = courseInstructorMapperDao;
    }

    public EmailNotifierImpl getEmailNotifier() {
        return emailNotifier;
    }


    public void setEmailNotifier(EmailNotifierImpl emailNotifier) {
        this.emailNotifier = emailNotifier;
    }

    public GroupFormationToolLogger getGroupFormationToolLogger() {
        return groupFormationToolLogger;
    }

    public void setGroupFormationToolLogger(GroupFormationToolLogger groupFormationToolLogger) {
        this.groupFormationToolLogger = groupFormationToolLogger;
    }

    public CourseStudentMapperDaoInjectorService getCourseStudentMapperDaoInjectorService() {
        return courseStudentMapperDaoInjectorService;
    }

    public void setCourseStudentMapperDaoInjectorService(CourseStudentMapperDaoInjectorService courseStudentMapperDaoInjectorService) {
        this.courseStudentMapperDaoInjectorService = courseStudentMapperDaoInjectorService;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public CourseInstructorMapperDaoInjectorService getCourseInstructorMapperDaoInjectorService() {
        return courseInstructorMapperDaoInjectorService;
    }

    public void setCourseInstructorMapperDaoInjectorService(CourseInstructorMapperDaoInjectorService courseInstructorMapperDaoInjectorService) {
        this.courseInstructorMapperDaoInjectorService = courseInstructorMapperDaoInjectorService;
    }

    public UserRoleDaoInjectorService getUserRoleDaoInjectorService() {
        return userRoleDaoInjectorService;
    }

    public void setUserRoleDaoInjectorService(UserRoleDaoInjectorService userRoleDaoInjectorService) {
        this.userRoleDaoInjectorService = userRoleDaoInjectorService;
    }


    public CourseStudentMapperDao getCourseStudentMapperDao() {
        return courseStudentMapperDao;
    }

    public void setCourseStudentMapperDao(CourseStudentMapperDao courseStudentMapperDao) {
        this.courseStudentMapperDao = courseStudentMapperDao;
    }

}