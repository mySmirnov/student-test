package net.mysmirnov.quiz;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        QuizApplication quizApplication = context.getBean(QuizApplication.class);
        quizApplication.run();
    }
}