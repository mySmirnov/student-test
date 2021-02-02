package net.mysmirnov.quiz;

import net.mysmirnov.quiz.utils.JdbcUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.SQLException;

public class Main {
    private static boolean setUpIsDone = false;

    public static void setUp() {
        if (!setUpIsDone) {
            boolean createConnection = JdbcUtils.createConnection();
            if (!createConnection) {
                throw new RuntimeException("Can't create connection, stop");
            }
            setUpIsDone = true;
        }

    }

    public static void close() {
        if (setUpIsDone) {
            JdbcUtils.closeConnection();
        }
    }

    public static void main(String[] args) throws SQLException {
        setUp();

        ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        QuizApplication quizApplication = context.getBean(QuizApplication.class);
        quizApplication.run();
        close();
    }
}